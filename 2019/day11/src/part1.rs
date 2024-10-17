use std::{collections::HashMap, fs};

#[derive(Debug)]
enum Mode {
    Position,
    Immediate,
    Relative,
}

impl Mode {
    fn new(mode: i64) -> Self {
        match mode {
            0 => Mode::Position,
            1 => Mode::Immediate,
            2 => Mode::Relative,
            _ => panic!("Invalid Mode!"),
        }
    }
}

#[derive(Debug, PartialEq)]
struct Param {
    params: Vec<usize>,
}

impl Param {
    fn new() -> Self {
        Param { params: Vec::new() }
    }

    fn add(
        &mut self,
        mode: Mode,
        mem: &mut HashMap<usize, i64>,
        ptr: usize,
        offset: usize,
        rel: usize,
    ) {
        match mode {
            Mode::Position => {
                let p = *mem.entry(ptr + offset).or_insert(0);
                self.params.push(p as usize)
            }
            Mode::Immediate => self.params.push(ptr + offset),
            Mode::Relative => {
                let p = *mem.entry(ptr + offset).or_insert(0) + (rel as i64);
                self.params.push(p as usize)
            }
        }
    }

    fn add_input(&mut self, val: usize) {
        self.params.push(val);
    }
}

#[derive(Debug, PartialEq)]
enum Command {
    Add(Param),
    Multiply(Param),
    Input(Param),
    Output(Param),
    JumpIfTrue(Param),
    JumpIfFalse(Param),
    LessThan(Param),
    Equals(Param),
    AdjustRelativeBase(Param),
    Exit,
}

impl Command {
    fn from(ptr: usize, rel: usize, mem: &mut HashMap<usize, i64>, input: Option<i64>) -> Self {
        let op_code_mode = *mem.entry(ptr).or_insert(0);
        let op_code = op_code_mode % 100;
        let mode_p1 = (op_code_mode / 100) % 10;
        let mode_p2 = (op_code_mode / 1000) % 10;
        let mode_p3 = (op_code_mode / 10000) % 10;
        let mut param = Param::new();
        match op_code {
            1 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                param.add(Mode::new(mode_p3), mem, ptr, 3, rel);
                Command::Add(param)
            }
            2 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                param.add(Mode::new(mode_p3), mem, ptr, 3, rel);
                Command::Multiply(param)
            }
            3 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add_input(input.unwrap() as usize);
                Command::Input(param)
            }
            4 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                Command::Output(param)
            }
            5 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                Command::JumpIfTrue(param)
            }
            6 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                Command::JumpIfFalse(param)
            }
            7 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                param.add(Mode::new(mode_p3), mem, ptr, 3, rel);
                Command::LessThan(param)
            }
            8 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                param.add(Mode::new(mode_p2), mem, ptr, 2, rel);
                param.add(Mode::new(mode_p3), mem, ptr, 3, rel);
                Command::Equals(param)
            }
            9 => {
                param.add(Mode::new(mode_p1), mem, ptr, 1, rel);
                Command::AdjustRelativeBase(param)
            }
            99 => Command::Exit,
            _ => panic!("Invalid Op-Code!"),
        }
    }

    fn exec(&self, ptr: &mut usize, rel: &mut usize, mem: &mut HashMap<usize, i64>) {
        match self {
            Command::Add(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                mem.insert(p.params[2], p0 + p1);
                *ptr += 4;
            }
            Command::Multiply(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                mem.insert(p.params[2], p0 * p1);
                *ptr += 4;
            }
            Command::Input(p) => {
                mem.insert(p.params[0], p.params[1] as i64);
                *ptr += 2;
            }
            Command::Output(_) => {
                *ptr += 2;
            }
            Command::JumpIfTrue(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                if p0 != 0 {
                    *ptr = p1 as usize;
                } else {
                    *ptr += 3;
                }
            }
            Command::JumpIfFalse(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                if p0 == 0 {
                    *ptr = p1 as usize;
                } else {
                    *ptr += 3;
                }
            }
            Command::LessThan(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                if p0 < p1 {
                    mem.insert(p.params[2], 1);
                } else {
                    mem.insert(p.params[2], 0);
                }
                *ptr += 4;
            }
            Command::Equals(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                let p1 = *mem.entry(p.params[1]).or_insert(0);
                if p0 == p1 {
                    mem.insert(p.params[2], 1);
                } else {
                    mem.insert(p.params[2], 0);
                }
                *ptr += 4;
            }
            Command::AdjustRelativeBase(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                *rel = ((*rel as i64) + p0) as usize;
                *ptr += 2;
            }
            Command::Exit => {
                println!("Exit");
            }
        }
    }
}

struct IntCodeComputer {
    relative_base: usize,
    ptr: usize,
    mem: HashMap<usize, i64>,
    out1: i64,
    hasout1: bool,
    out2: i64,
}

impl IntCodeComputer {
    fn new(mem: &HashMap<usize, i64>) -> Self {
        let mem = mem.clone();
        Self {
            relative_base: 0,
            ptr: 0,
            mem,
            out1: 0,
            hasout1: false,
            out2: 0,
        }
    }

    fn run(&mut self, signal: i64) -> (i64, i64, bool) {
        let mut command: Command;
        loop {
            let op = *self.mem.entry(self.ptr).or_insert(0) % 100;
            if op == 3 {
                command = Command::from(self.ptr, self.relative_base, &mut self.mem, Some(signal));
            } else if op == 99 {
                return (self.out1, self.out2, true);
            } else {
                command = Command::from(self.ptr, self.relative_base, &mut self.mem, None);
            }
            if let Command::Output(ref p) = command {
                if !self.hasout1 {
                    self.hasout1 = true;
                    self.out1 = *self.mem.entry(p.params[0]).or_insert(0);

                    println!("\nNEW ROUND");
                    println!("1: {} {} {}", self.hasout1, self.out1, self.out2);
                } else {
                    self.out2 = *self.mem.entry(p.params[0]).or_insert(0);
                    println!("2: {} {} {}", self.hasout1, self.out1, self.out2);
                    self.hasout1 = false;
                    return (self.out1, self.out2, false);

                    // problem might be that we are not executing the output command right:
                }
            }
            command.exec(&mut self.ptr, &mut self.relative_base, &mut self.mem);
        }
    }
}

struct Robot {
    x: i64,
    y: i64,
    angle: i64,
}

impl Robot {
    fn new() -> Self {
        Self {
            x: 0,
            y: 0,
            angle: 0,
        }
    }

    fn step(&mut self) {
        match self.angle {
            0 => self.y += 1,
            90 => self.x += 1,
            180 => self.y -= 1,
            270 => self.x -= 1,
            _ => panic!("Invalid angle!"),
        }
    }

    fn turn(&mut self, dir: i64) {
        if dir == 0 {
            self.angle = (360 + self.angle - 90) % 360;
        } else {
            self.angle = (360 + self.angle + 90) % 360;
        }
        self.step();
    }
}

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();
    let mem: HashMap<usize, i64> = input
        .split(',')
        .enumerate()
        .map(|(i, s)| (i, s.parse::<i64>().unwrap()))
        .collect();

    let mut computer = IntCodeComputer::new(&mem);
    let mut robot = Robot::new();

    let mut grid: HashMap<(i64, i64), i64> = HashMap::new();

    loop {
        let input = *grid.entry((robot.x, robot.y)).or_insert(0);

        let (color, dir, halted) = computer.run(input);

        grid.insert((robot.x, robot.y), color);
        robot.turn(dir);

        if halted {
            break;
        }
    }
    println!("Part1: {}", grid.keys().len());
}
