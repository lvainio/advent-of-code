use std::{fs, num::ParseIntError};

pub fn part1() {
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

    nums[1] = 12;
    nums[2] = 2;

    let mut op_code = nums[0];
    let mut idx = 0;
    while op_code != 99 {
        if op_code == 1 {
            let a = nums[nums[idx + 1] as usize];
            let b = nums[nums[idx + 2] as usize];
            let out_idx = nums[idx + 3] as usize;
            nums[out_idx] = a + b;
        } else {
            let a = nums[nums[idx + 1] as usize];
            let b = nums[nums[idx + 2] as usize];
            let out_idx = nums[idx + 3] as usize;
            nums[out_idx] = a * b;
        }
        idx += 4;
        op_code = nums[idx];
    }

    println!("Part1: {}", nums[0]);
}
