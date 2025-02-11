use std::fs;

#[derive(Debug, PartialEq)]
enum Command {
    Add(i64, i64, i64),
    Multiply(i64, i64, i64),
    Input(i64, i64),
    Output(i64),
    Exit,
}

impl Command {
    fn from(ptr: &mut usize, program: &Vec<i64>, input: Option<i64>) -> Self {
        let op_code = program[*ptr] % 100;
        let mode_p1 = (program[*ptr] / 100) % 10;
        let mode_p2 = (program[*ptr] / 1000) % 10;
        
        match op_code {
            1 => {
                let p1 = if mode_p1 == 0 {
                    program[program[*ptr + 1] as usize]
                } else {
                    program[*ptr + 1]
                };
                let p2 = if mode_p2 == 0 {
                    program[program[*ptr + 2] as usize]
                } else {
                    program[*ptr + 2]
                };
                let p3 = program[*ptr + 3];
                *ptr += 4;
                Command::Add(p1, p2, p3)
            }
            2 => {
                let p1 = if mode_p1 == 0 {
                    program[program[*ptr + 1] as usize]
                } else {
                    program[*ptr + 1]
                };
                let p2 = if mode_p2 == 0 {
                    program[program[*ptr + 2] as usize]
                } else {
                    program[*ptr + 2]
                };
                let p3 = program[*ptr + 3];

                *ptr += 4;
                Command::Multiply(p1, p2, p3)
            }
            3 => {
                let p1 = program[*ptr + 1];
                let p2 = input.unwrap();
                *ptr += 2;
                Command::Input(p1, p2)
            }
            4 => {
                let p = if mode_p1 == 0 {
                    program[program[*ptr + 1] as usize]
                } else {
                    program[*ptr]
                };
                *ptr += 2;
                Command::Output(p)
            }
            99 => Command::Exit,
            _ => panic!("Invalid Op-Code!"),
        }
    }

    fn exec(&self, program: &mut Vec<i64>) {
        match self {
            Command::Add(p1, p2, p3) => {
                program[*p3 as usize] = p1 + p2;
            }
            Command::Multiply(p1, p2, p3) => {
                program[*p3 as usize] = p1 * p2;
            }
            Command::Input(p1, p2) => {
                program[*p1 as usize] = *p2;
            }
            Command::Output(p1) => {
                println!("Output: {}", *p1);
            }
            Command::Exit => {
                println!("Exit");
            }
        }
    }
}

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();
    let mut program: Vec<i64> = input
        .split(',')
        .map(|s| s.parse::<i64>().unwrap())
        .collect();

    let mut ptr: usize = 0;
    let mut command = Command::from(&mut ptr, &program, Some(1));

    while command != Command::Exit {
        command.exec(&mut program);
        command = Command::from(&mut ptr, &program, None);
    }
}
