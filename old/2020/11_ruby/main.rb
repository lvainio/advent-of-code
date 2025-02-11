

def parse_data
    lines = File.read("input.txt").split("\n")
    grid = lines.map { |line| line.split('') }

    return grid
end


def count_adjecent (grid, row, col)
    rows = grid.length
    cols = grid[0].length

    directions = [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]]
    count = 0

    directions.each do |dr, dc|
        nr = row + dr
        nc = col + dc
        if nr.between?(0, rows-1) && nc.between?(0, cols-1) && grid[nr][nc] == '#'
            count += 1
        end
    end
    return count
end


def update_seats (grid)
    rows = grid.length
    cols = grid[0].length

    new_grid = Array.new(rows) { Array.new(cols) }
    for row in 0...rows do
        for col in 0...cols do
            if grid[row][col] == 'L' && count_adjecent(grid, row, col) == 0
                new_grid[row][col] = '#'
            elsif grid[row][col] == '#' && count_adjecent(grid, row, col) >= 4
                new_grid[row][col] = 'L'
            else
                new_grid[row][col] = grid[row][col]
            end  
        end
    end
    return new_grid
end


def part1 (grid)
    prev_grid = grid
    new_grid = update_seats(grid)

    while prev_grid != new_grid do
        prev_grid = new_grid
        new_grid = update_seats(new_grid)
    end

    rows = new_grid.length
    cols = new_grid[0].length
    count = 0
    for row in 0...rows do
        for col in 0...cols do
            if new_grid[row][col] == '#' 
                count += 1
            end
        end
    end
    return count
end


def count_adjecent2 (grid, row, col)
    rows = grid.length
    cols = grid[0].length

    directions = [[-1, -1], [-1, 0], [-1, 1], [0, -1], [0, 1], [1, -1], [1, 0], [1, 1]]
    count = 0

    directions.each do |dr, dc|
        nr = row + dr
        nc = col + dc
        while nr.between?(0, rows-1) && nc.between?(0, cols-1) && grid[nr][nc] == '.' do
            nr += dr
            nc += dc
        end
        if nr.between?(0, rows-1) && nc.between?(0, cols-1) && grid[nr][nc] == '#'
            count += 1
        end
    end
    return count
end


def update_seats2 (grid)
    rows = grid.length
    cols = grid[0].length

    new_grid = Array.new(rows) { Array.new(cols) }
    for row in 0...rows do
        for col in 0...cols do
            if grid[row][col] == 'L' && count_adjecent2(grid, row, col) == 0
                new_grid[row][col] = '#'
            elsif grid[row][col] == '#' && count_adjecent2(grid, row, col) >= 5
                new_grid[row][col] = 'L'
            else
                new_grid[row][col] = grid[row][col]
            end  
        end
    end
    return new_grid
end


def part2 (grid)
    prev_grid = grid
    new_grid = update_seats(grid)

    while prev_grid != new_grid do
        prev_grid = new_grid
        new_grid = update_seats2(new_grid)
    end

    rows = new_grid.length
    cols = new_grid[0].length
    count = 0
    for row in 0...rows do
        for col in 0...cols do
            if new_grid[row][col] == '#' 
                count += 1
            end
        end
    end
    return count
end


grid = parse_data

puts part1(grid)
puts part2(grid)



