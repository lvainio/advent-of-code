use std::fs;

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

    fn generate_image(&self) -> String {
        let num_layers = self.layers.len();
        let mut message = String::new();
        for r in 0..6 {
            for c in 0..25 {
                for l in 0..num_layers {
                    match self.layers[l].get_pixel(r, c) {
                        0 => {
                            message.push('#');
                            break;
                        }
                        1 => {
                            message.push(' ');
                            break;
                        }
                        _ => continue,
                    }
                }
            }
            message.push('\n');
        }
        message
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

    fn get_pixel(&self, row: usize, col: usize) -> u64 {
        self.layer[row][col]
    }
}

pub fn solve() {
    let input: String = fs::read_to_string("input.txt").unwrap();
    let input: Vec<u64> = input
        .chars()
        .map(|c| c.to_digit(10).unwrap() as u64)
        .collect();

    let image = Image::new(&input, 25, 6);

    let solution = image.generate_image();

    println!("Part2: (Squint)\n{solution}");
}
