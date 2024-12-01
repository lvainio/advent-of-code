use std::collections::HashMap;
use std::fs;

pub fn part2() {
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

    let mut counts: HashMap<i64, i64> = HashMap::new();
    for val in right {
        *counts.entry(val).or_insert(0) += 1;
    }

    let mut sum = 0;
    for val in left {
        let count = counts.get(&val).unwrap_or(&0);

        sum += count * val;
    }

    println!("part2: {sum}");
}
