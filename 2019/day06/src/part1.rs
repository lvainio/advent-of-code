use std::{
    collections::{HashMap, VecDeque},
    fs,
};

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();

    let mut map: HashMap<String, Vec<String>> = HashMap::new();
    for line in input.lines() {
        let objects: Vec<&str> = line.split(')').collect();
        map.entry(String::from(objects[0]))
            .or_insert_with(Vec::new)
            .push(String::from(objects[1]));
    }

    let mut queue: VecDeque<(String, u32)> = VecDeque::new();
    queue.push_back(("COM".to_string(), 0));

    let mut total_depth = 0;
    while let Some((current_node, depth)) = queue.pop_front() {
        total_depth += depth;

        if let Some(neighbors) = map.get(&current_node) {
            for neighbor in neighbors {
                queue.push_back((neighbor.clone(), depth + 1));
            }
        }
    }
    println!("Part1: {total_depth}");
}
