#[derive(Default, Debug)]
struct Body {
    x: i32,
    y: i32,
    z: i32,
    dx: i32,
    dy: i32,
    dz: i32,
}

impl Body {
    fn new(x: i32, y: i32, z: i32) -> Self {
        Body {
            x,
            y,
            z,
            ..Default::default()
        }
    }

    fn step(&mut self) {
        self.x += self.dx;
        self.y += self.dy;
        self.z += self.dz;
    }
}

pub fn solve() {
    let mut bodies = vec![
        Body::new(3, 2, -6),
        Body::new(-13, 18, 10),
        Body::new(-8, -1, 13),
        Body::new(5, 10, 4),
    ];

    for _ in 0..1000 {
        for i in 0..bodies.len() {
            for j in (i + 1)..bodies.len() {
                if bodies[i].x < bodies[j].x {
                    bodies[i].dx += 1;
                    bodies[j].dx -= 1;
                } else if bodies[i].x > bodies[j].x {
                    bodies[i].dx -= 1;
                    bodies[j].dx += 1;
                }
                if bodies[i].y < bodies[j].y {
                    bodies[i].dy += 1;
                    bodies[j].dy -= 1;
                } else if bodies[i].y > bodies[j].y {
                    bodies[i].dy -= 1;
                    bodies[j].dy += 1;
                }
                if bodies[i].z < bodies[j].z {
                    bodies[i].dz += 1;
                    bodies[j].dz -= 1;
                } else if bodies[i].z > bodies[j].z {
                    bodies[i].dz -= 1;
                    bodies[j].dz += 1;
                }
            }
        }
        for body in bodies.iter_mut() {
            body.step();
        }
    }

    let mut total: i32 = 0;
    for body in bodies {
        println!(
            "({}, {}, {}), ({}, {}, {})",
            body.x, body.y, body.z, body.dx, body.dy, body.dz
        );
        total += (body.x.abs() + body.y.abs() + body.z.abs())
            * (body.dx.abs() + body.dy.abs() + body.dz.abs());
    }
    println!("Part1: {total}");
}
