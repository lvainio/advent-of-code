#!/usr/bin/perl

use strict;
use warnings;

my @nums = (0,12,6,13,20,1,17);   

my %hash;
for (my $i = 1; $i < scalar @nums; $i++) {
    $hash{$nums[$i-1]} = $i;
}

my $last_spoken = $nums[-1]; 
my $turn = scalar @nums + 1;  

while ($turn <= 30000000) { # change to 2020 for part1
    if (exists $hash{$last_spoken}) { 
        my $speak = $turn - $hash{$last_spoken} - 1;
        $hash{$last_spoken} = $turn - 1;
        $last_spoken = $speak;
    } else {
        $hash{$last_spoken} = $turn - 1;
        $last_spoken = 0;        
    }
    $turn++;
}

print "$last_spoken\n";





