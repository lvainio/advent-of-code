<?php 

function part1() {
    $lines = file('./input.txt');
    $mask = null;
    $memory = array();

    foreach($lines as $line) {
        if (strcmp(substr($line, 0, 4), "mask") === 0) {
            $mask = trim(explode("=", $line)[1]);

        } elseif (strcmp(substr($line, 0, 3), "mem") === 0) {
            preg_match_all('/\d+/', $line, $matches);

            $address = intval($matches[0][0]);
            $value = decbin(intval($matches[0][1]));
            $zeros = str_repeat("0", 36);

            for ($i = strlen($value)-1, $j = 35; $i >= 0; $i--, $j--) {
                $zeros[$j] = $value[$i];
            }
            for ($i = 35; $i >= 0; $i--) {
                if ($mask[$i] !== 'X') {
                    $zeros[$i] = $mask[$i];
                }
            }

            $new_value = intval($zeros, 2);
            $memory[$address] = $new_value;
        }
    }

    $total = 0;
    foreach ($memory as $value) {
        $total += $value;
    }
    echo $total . "\n";
}

function add_combinations($address, &$memory, $value, $index = 0) {
    $pos = strpos($address, 'X', $index);
    
    if ($pos === false) {
        $int_address = intval($address, 2);
        $memory[$int_address] = $value;
        return;
    }

    $address[$pos] = '0';
    add_combinations($address, $memory, $value, $pos + 1);

    $address[$pos] = '1';
    add_combinations($address, $memory, $value, $pos + 1);
}

function part2() {
    $lines = file('./input.txt');
    $mask = null;
    $memory = array();

    foreach($lines as $line) {
        if (strcmp(substr($line, 0, 4), "mask") === 0) {
            $mask = trim(explode("=", $line)[1]);

        } elseif (strcmp(substr($line, 0, 3), "mem") === 0) {
            preg_match_all('/\d+/', $line, $matches);

            $address = decbin(intval($matches[0][0]));
            $value = intval($matches[0][1]);
            $masked_address = str_repeat("0", 36);

            for ($i = strlen($address)-1, $j = 35; $i >= 0; $i--, $j--) {
                $masked_address[$j] = $address[$i];
            }
            for ($i = 35; $i >= 0; $i--) {
                if ($mask[$i] !== '0') {
                    $masked_address[$i] = $mask[$i];
                }
            }
            add_combinations($masked_address, $memory, $value);
        }
    }

    $total = 0;
    foreach ($memory as $value) {
        $total += $value;
    }
    echo $total . "\n";
}

part1();
part2();

?>