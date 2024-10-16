use std::{fs, i64};

#[derive(Debug)]
struct Image {
    layers: Vec<ImageLayer>,
}

impl Image {
    fn new(image: &Vec<u64>, width: u64, height: u64) -> Self {
        let pixels_in_layer = (width * height) as usize;
        let mut layers: Vec<ImageLayer> = Vec::new();
        for layer in image.chunks(pixels_in_layer) {
            layers.push(ImageLayer::new(layer.to_vec(), width));
        }
        Self { layers }
    }

    fn part1(&self) -> i64 {
        let mut fewest_zeroes = i64::MAX;
        let mut result: i64 = 0;
        for layer in &self.layers {
            let num_zeros = layer.count_digit(0);
            if num_zeros < fewest_zeroes {
                let num_ones = layer.count_digit(1);
                let num_twos = layer.count_digit(2);
                result = num_ones * num_twos;
                fewest_zeroes = num_zeros
            }
        }
        result
    }
}

#[derive(Debug)]
struct ImageLayer {
    layer: Vec<Vec<u64>>,
}

impl ImageLayer {
    fn new(layer: Vec<u64>, width: u64) -> Self {
        let mut rows: Vec<Vec<u64>> = Vec::new();
        for row in layer.chunks(width as usize) {
            rows.push(row.to_vec());
        }
        Self { layer: rows }
    }

    fn count_digit(&self, digit: u64) -> i64 {
        let mut count = 0;
        for row in &self.layer {
            count += row.iter().filter(|&&x| x == digit).count();
        }
        count as i64
    }
}

pub fn solve() {
    let input: String = fs::read_to_string("input.txt").unwrap();
    let input: Vec<u64> = input
        .chars()
        .map(|c| c.to_digit(10).unwrap() as u64)
        .collect();

    let image = Image::new(&input, 25, 6);

    let solution = image.part1();

    println!("Part1: {solution}");
}
