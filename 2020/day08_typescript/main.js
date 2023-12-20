"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var fs_1 = require("fs");
function parseData() {
    var instructions = [];
    try {
        (0, fs_1.readFileSync)('input.txt', 'utf-8').split(/\r?\n/).forEach(function (line) {
            var tokens = line.split(' ');
            instructions.push([tokens[0], Number(tokens[1])]);
        });
    }
    catch (e) {
        throw (e);
    }
    return instructions;
}
function part1(instructions) {
    var visited = new Set();
    var ptr = 0;
    var acc = 0;
    while (!visited.has(ptr)) {
        visited.add(ptr);
        var _a = instructions[ptr], instruction = _a[0], arg = _a[1];
        if (instruction === 'nop') {
            ptr += 1;
        }
        else if (instruction === 'acc') {
            acc += arg;
            ptr += 1;
        }
        else if (instruction === 'jmp') {
            ptr += arg;
        }
    }
    return acc;
}
function part2(instructions) {
    var visited = new Set();
    var ptr = 0;
    var acc = 0;
    while (!visited.has(ptr)) {
        if (ptr === instructions.length) {
            break;
        }
        visited.add(ptr);
        var _a = instructions[ptr], instruction = _a[0], arg = _a[1];
        if (instruction === 'nop') {
            ptr += 1;
        }
        else if (instruction === 'acc') {
            acc += arg;
            ptr += 1;
        }
        else if (instruction === 'jmp') {
            ptr += arg;
        }
    }
    return [acc, ptr === instructions.length];
}
var instructions = parseData();
console.log(part1(instructions));
for (var i = 0; i < instructions.length; i += 1) {
    var instruction = instructions[i][0];
    if (instruction === 'nop' || instruction === 'jmp') {
        var originalInstruction = instruction;
        instructions[i][0] = instruction === 'nop' ? 'jmp' : 'nop';
        var _a = part2(instructions), acc = _a[0], found = _a[1];
        instructions[i][0] = originalInstruction;
        if (found) {
            console.log(acc);
            break;
        }
    }
}
