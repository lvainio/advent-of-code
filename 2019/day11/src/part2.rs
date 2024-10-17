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
            Command::Output(p) => {
                let p0 = *mem.entry(p.params[0]).or_insert(0);
                println!("Output: {p0}");
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

struct Amplifier {
    relative_base: usize,
    ptr: usize,
    mem: HashMap<usize, i64>,
}

impl Amplifier {
    fn new(mem: &HashMap<usize, i64>) -> Self {
        let mem = mem.clone();
        Self {
            relative_base: 0,
            ptr: 0,
            mem,
        }
    }

    fn run(&mut self, signal: i64) -> (i64, bool) {
        let mut command: Command;
        let mut output: i64 = 0;
        loop {
            let op = *self.mem.entry(self.ptr).or_insert(0) % 100;
            if op == 3 {
                command = Command::from(self.ptr, self.relative_base, &mut self.mem, Some(signal));
            } else if op == 99 {
                return (output, true);
            } else {
                command = Command::from(self.ptr, self.relative_base, &mut self.mem, None);
            }
            if let Command::Output(ref p) = command {
                output = *self.mem.entry(p.params[0]).or_insert(0);
            }
            command.exec(&mut self.ptr, &mut self.relative_base, &mut self.mem);
        }
    }
}

pub fn solve() {}
