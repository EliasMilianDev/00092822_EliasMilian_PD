
suma(A, B, Resultado) :-
    number(A), number(B),
    Resultado is A + B.

resta(A, B, Resultado) :-
    number(A), number(B),
    Resultado is A - B.

multiplicacion(A, B, Resultado) :-
    number(A), number(B),
    Resultado is A * B.

division(A, B, Resultado) :-
    number(A), number(B), B =\= 0,
    Resultado is A / B.
