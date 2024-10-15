use std::{fs, num::ParseIntError};

pub fn part2() {
    let content = match fs::read_to_string("input.txt") {
        Ok(c) => c,
        Err(error) => panic!("Could not read file {error:?}"),
    };

    let nums: Result<Vec<i64>, ParseIntError> = content
        .trim()
        .split(',')
        .map(|s| s.parse::<i64>())
        .collect();

    let mut nums = match nums {
        Ok(n) => n,
        Err(error) => panic!("Could not convert to vector {error:?}"),
    };

    let output_goal: i64 = 19_690_720;

    for noun in 0..100 {
        for verb in 0..100 {
            let mut op_code: i64 = nums[0];
            let mut instruction_pointer: usize = 0;

            let nums_copy = nums.clone();
            nums[1] = noun;
            nums[2] = verb;

            while op_code != 99 {
                if op_code == 1 {
                    let a = nums[nums[instruction_pointer + 1] as usize];
                    let b = nums[nums[instruction_pointer + 2] as usize];
                    let out_instruction_pointer = nums[instruction_pointer + 3] as usize;
                    nums[out_instruction_pointer] = a + b;
                } else {
                    let a = nums[nums[instruction_pointer + 1] as usize];
                    let b = nums[nums[instruction_pointer + 2] as usize];
                    let out_instruction_pointer = nums[instruction_pointer + 3] as usize;
                    nums[out_instruction_pointer] = a * b;
                }
                instruction_pointer += 4;
                op_code = nums[instruction_pointer];
            }

            if nums[0] == output_goal {
                println!("Part2: {}", 100 * noun + verb);
                return;
            }

            nums = nums_copy.clone();
        }
    }
}
