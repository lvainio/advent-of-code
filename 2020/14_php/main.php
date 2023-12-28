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

part1();

?>