use itertools::Itertools;
use std::fs;

enum Mode {
    Positional,
    Immediate,
}

impl Mode {
    fn new(mode: i64) -> Self {
        match mode {
            0 => Mode::Positional,
            1 => Mode::Immediate,
            _ => panic!("Invalid Mode!"),
        }
    }
}

#[derive(Debug, PartialEq)]
enum Command {
    Add(i64, i64, i64),
    Multiply(i64, i64, i64),
    Input(i64, i64),
    Output(i64),
    JumpIfTrue(i64, i64),
    JumpIfFalse(i64, i64),
    LessThan(i64, i64, i64),
    Equals(i64, i64, i64),
    Exit,
}

impl Command {
    fn get_param(program: &Vec<i64>, ptr: usize, offset: usize, mode: Mode) -> i64 {
        match mode {
            Mode::Positional => program[program[ptr + offset] as usize],
            Mode::Immediate => program[ptr + offset],
        }
    }

    fn from(ptr: usize, program: &Vec<i64>, input: Option<i64>) -> Self {
        let op_code = program[ptr] % 100;
        let mode_p1 = (program[ptr] / 100) % 10;
        let mode_p2 = (program[ptr] / 1000) % 10;

        match op_code {
            1 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                let p3 = Self::get_param(program, ptr, 3, Mode::new(1));
                Command::Add(p1, p2, p3)
            }
            2 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                let p3 = Self::get_param(program, ptr, 3, Mode::new(1));
                Command::Multiply(p1, p2, p3)
            }
            3 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(1));
                let p2 = input.unwrap();
                Command::Input(p1, p2)
            }
            4 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                Command::Output(p1)
            }
            5 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                Command::JumpIfTrue(p1, p2)
            }
            6 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                Command::JumpIfFalse(p1, p2)
            }
            7 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                let p3 = Self::get_param(program, ptr, 3, Mode::new(1));
                Command::LessThan(p1, p2, p3)
            }
            8 => {
                let p1 = Self::get_param(program, ptr, 1, Mode::new(mode_p1));
                let p2 = Self::get_param(program, ptr, 2, Mode::new(mode_p2));
                let p3 = Self::get_param(program, ptr, 3, Mode::new(1));
                Command::Equals(p1, p2, p3)
            }
            99 => Command::Exit,
            _ => panic!("Invalid Op-Code!"),
        }
    }

    fn exec(&self, ptr: &mut usize, program: &mut Vec<i64>) {
        match self {
            Command::Add(p1, p2, p3) => {
                program[*p3 as usize] = p1 + p2;
                *ptr += 4;
            }
            Command::Multiply(p1, p2, p3) => {
                program[*p3 as usize] = p1 * p2;
                *ptr += 4;
            }
            Command::Input(p1, input) => {
                program[*p1 as usize] = *input;
                *ptr += 2;
            }
            Command::Output(_) => {
                *ptr += 2;
            }
            Command::JumpIfTrue(p1, p2) => {
                if *p1 != 0 {
                    *ptr = *p2 as usize;
                } else {
                    *ptr += 3;
                }
            }
            Command::JumpIfFalse(p1, p2) => {
                if *p1 == 0 {
                    *ptr = *p2 as usize;
                } else {
                    *ptr += 3;
                }
            }
            Command::LessThan(p1, p2, p3) => {
                if p1 < p2 {
                    program[*p3 as usize] = 1;
                } else {
                    program[*p3 as usize] = 0;
                }
                *ptr += 4;
            }
            Command::Equals(p1, p2, p3) => {
                if p1 == p2 {
                    program[*p3 as usize] = 1;
                } else {
                    program[*p3 as usize] = 0;
                }
                *ptr += 4;
            }
            Command::Exit => {
                println!("Exit");
            }
        }
    }
}

struct Amplifier {
    has_phase: bool,
    has_signal: bool,
    phase: i64,
    ptr: usize,
    program: Vec<i64>,
}

impl Amplifier {
    fn new(program: &Vec<i64>, phase: i64) -> Self {
        let program = program.clone();
        Self {
            has_phase: false,
            has_signal: false,
            phase,
            ptr: 0,
            program,
        }
    }

    fn run(&mut self, signal: i64) -> (i64, bool) {
        let mut command: Command;
        let mut output: i64 = 0;
        loop {
            if self.program[self.ptr] == 3 {
                if !self.has_phase {
                    command = Command::from(self.ptr, &self.program, Some(self.phase));
                    self.has_phase = true;
                } else if !self.has_signal {
                    command = Command::from(self.ptr, &self.program, Some(signal));
                    self.has_signal = true;
                } else {
                    self.has_signal = false;
                    return (output, false);
                }
            } else if self.program[self.ptr] == 99 {
                return (output, true);
            } else {
                command = Command::from(self.ptr, &self.program, None);
            }
            if let Command::Output(p1) = command {
                output = p1;
            }
            command.exec(&mut self.ptr, &mut self.program);
        }
    }
}

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();
    let program: Vec<i64> = input
        .split(',')
        .map(|s| s.parse::<i64>().unwrap())
        .collect();

    let mut max_strength = i64::MIN;
    for perm in (5..=9).permutations(5) {
        let mut amp_a = Amplifier::new(&program, perm[0]);
        let mut amp_b = Amplifier::new(&program, perm[1]);
        let mut amp_c = Amplifier::new(&program, perm[2]);
        let mut amp_d = Amplifier::new(&program, perm[3]);
        let mut amp_e = Amplifier::new(&program, perm[4]);

        let mut signal = 0;
        loop {
            let (output, _) = amp_a.run(signal);
            let (output, _) = amp_b.run(output);
            let (output, _) = amp_c.run(output);
            let (output, _) = amp_d.run(output);
            let (output, halted) = amp_e.run(output);
            signal = output;

            if halted {
                if signal > max_strength {
                    max_strength = signal;
                }
                break;
            }
        }
    }
    println!("Part2: {max_strength}");
}
