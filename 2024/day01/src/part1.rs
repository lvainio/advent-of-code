use std::fs;

pub fn part1() {
    let content = match fs::read_to_string("input.txt") {
        Ok(c) => c,
        Err(error) => panic!("Problem opening the file: {error:?}"),
    };

    let lines = content.lines();

    let mut left: Vec<i64> = vec![];
    let mut right: Vec<i64> = vec![];

    for line in lines {
        let vals: Vec<i64> = line
            .split_whitespace()
            .map(|s| s.parse::<i64>().expect("Failed to parse integer"))
            .collect();

        left.push(vals[0]);
        right.push(vals[1]);
    }

    left.sort();
    right.sort();

    let mut sum = 0;
    for (l, r) in left.iter().zip(right.iter()) {
        sum += (l - r).abs();
    }

    println!("part1: {sum}");
}
