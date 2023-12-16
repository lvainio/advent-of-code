main :-
    read_file(Lines),
    find_max_id(Lines, 0, [], Max, IDs),
    write("Part1: "), write(Max), nl,
    sort(IDs, SortedIDs),
    find_seat(SortedIDs, Seat),
    write("Part2: "), write(Seat), nl,
    !.

read_file(Lines) :-
    setup_call_cleanup(
        open('input.txt', read, In), 
        split_lines(In, Lines), 
        close(In)
    ).

split_lines(In, Lines) :-
    read_string(In, _, Str),
    split_string(Str, "\n", "", Lines).

find_max_id([], Curr, IDs, Max, List) :- 
    Max = Curr,
    List = IDs.
find_max_id([H|T], Curr, IDs, Max, List) :-
    calculate_id(H, ID),
    Next is max(Curr, ID),
    find_max_id(T, Next, [ID|IDs], Max, List).

calculate_id(Str, ID) :-
    string_chars(Str, Chars),
    take(7, Chars, RowChars),
    drop(7, Chars, ColChars), 
    get_row(RowChars, 0, 127, Row),
    get_col(ColChars, 0, 7, Col),
    ID is Row * 8 + Col.

take(0, _, []).
take(N, [H|T], [H|Rest]) :-
    N > 0,
    N1 is N - 1,
    take(N1, T, Rest).

drop(0, List, List).
drop(N, [_|T], Rest) :-
    N > 0,
    N1 is N - 1,
    drop(N1, T, Rest).

get_row([], Row, Row, Row).
get_row(['F'|T], Lower, Upper, Row) :-
    NewUpper is (Lower+Upper) // 2,
    get_row(T, Lower, NewUpper, Row).
get_row(['B'|T], Lower, Upper, Row) :-
    NewLower is (Lower+Upper) // 2 + 1,
    get_row(T, NewLower, Upper, Row).

get_col([], Col, Col, Col). 
get_col(['L'|T], Lower, Upper, Col) :-
    NewUpper is (Lower+Upper) // 2,
    get_col(T, Lower, NewUpper, Col).
get_col(['R'|T], Lower, Upper, Col) :-
    NewLower is ((Lower+Upper) // 2) + 1,
    get_col(T, NewLower, Upper, Col).

find_seat([A, B|_], Seat) :-
    A =:= B - 2,
    Seat is A + 1.
find_seat([_|T], Seat) :-
    find_seat(T, Seat).