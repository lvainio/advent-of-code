import { readFileSync } from 'fs'

type Instruction = [string, number];

function parseData(): Instruction[] {
    const instructions: Instruction[] = []
    try {
        readFileSync('input.txt', 'utf-8').split(/\r?\n/).forEach(line => { 
            const tokens: string[] = line.split(' ') 
            instructions.push([tokens[0], Number(tokens[1])])
        })
    } catch (e) {
        throw (e)
    }
    return instructions
}

function part1(instructions: Instruction[]): number {
    const visited = new Set<number>();
    let ptr: number = 0
    let acc: number = 0
    while (!visited.has(ptr)) {
        visited.add(ptr)
        const [instruction, arg]: Instruction = instructions[ptr]
        if (instruction === 'nop') {
            ptr += 1
        } else if (instruction === 'acc') {
            acc += arg
            ptr += 1
        } else if (instruction === 'jmp') {
            ptr += arg
        } 
    }
    return acc
}

function part2(instructions: Instruction[]): [number, boolean] {
    const visited = new Set<number>();
    let ptr: number = 0
    let acc: number = 0
    while (!visited.has(ptr)) {
        if (ptr === instructions.length) {
            break
        }
        visited.add(ptr)
        const [instruction, arg]: Instruction = instructions[ptr]
        if (instruction === 'nop') {
            ptr += 1
        } else if (instruction === 'acc') {
            acc += arg
            ptr += 1
        } else if (instruction === 'jmp') {
            ptr += arg
        } 
    }
    return [acc, ptr === instructions.length]
}

const instructions: Instruction[] = parseData()
console.log(part1(instructions))

for (let i = 0; i < instructions.length; i += 1) {
    const instruction: string = instructions[i][0]
    if (instruction === 'nop' || instruction === 'jmp') {
        const originalInstruction: string = instruction
        instructions[i][0] = instruction === 'nop' ? 'jmp' : 'nop';
        const [acc, found]: [number, boolean] = part2(instructions);
        instructions[i][0] = originalInstruction;
        if (found) {
            console.log(acc)
            break
        }
    }
}

