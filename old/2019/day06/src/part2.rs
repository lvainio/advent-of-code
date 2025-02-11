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
        map.entry(String::from(objects[1]))
            .or_insert_with(Vec::new)
            .push(String::from(objects[0]));
    }

    let mut queue: VecDeque<(String, u32)> = VecDeque::new();
    queue.push_back(("YOU".to_string(), 0));

    let mut visited: HashMap<String, bool> = HashMap::new();
    visited.insert("YOU".to_string(), true);

    while let Some((current_node, depth)) = queue.pop_front() {
        if let Some(neighbors) = map.get(&current_node) {
            for neighbor in neighbors {
                if !visited.contains_key(neighbor) {
                    visited.insert(neighbor.clone(), true);
                    if neighbor == "SAN" {
                        println!("Part2 {}", depth - 1);
                        return;
                    }
                    queue.push_back((neighbor.clone(), depth + 1));
                } 
            }
        }
    }
}
