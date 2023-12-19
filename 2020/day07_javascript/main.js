const fs = require('node:fs');

function parseData() {
    let data
    try {
        data = fs.readFileSync('input.txt', 'utf8')
    } catch (err) {
        console.error(err);
    }
    const bags = new Map()
    const lines = data.split('\r\n')
    lines.forEach(line => {
        const tokens = line.split(' ')
        const bagColor = tokens[0] + tokens[1]
        const bagsContained = []
        if (tokens[4] !== 'no') {
            let index = 4
            while (index < tokens.length) {
                const quantity = Number(tokens[index])
                const color = tokens[index+1] + tokens[index+2]
                bagsContained.push([quantity, color])
                index += 4
            }
        }
        bags.set(bagColor, bagsContained)
    });
    return bags
}

function containsShiny(bags, bag) {
    const bagContents = bags.get(bag)
    for (let i = 0; i < bagContents.length; i++) {
        const color = bagContents[i][1]
        if (color === 'shinygold' || containsShiny(bags, color)) {
            return true
        }
    }  
    return false
}

function countBagsContained(bags, bag) {
    const bagContents = bags.get(bag)
    if (bagContents.length === 0) {
        return 0
    }
    let count = 0
    for (let i = 0; i < bagContents.length; i++) {
        const [quantity, color] = bagContents[i]
        count += quantity + quantity * countBagsContained(bags, color) 
    }  
    return count
}



const bags = parseData()

let part1 = 0
for (const bag of bags.keys()) {
    if (containsShiny(bags, bag)) {
        part1 += 1
    }
}
let part2 = countBagsContained(bags, 'shinygold')

console.log(part1)
console.log(part2)



