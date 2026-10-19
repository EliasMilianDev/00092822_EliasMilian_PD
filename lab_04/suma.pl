% Ejercicio 2: dado un numero N, sumar N con todos los anteriores hasta llegar a 1.
% suma(N, S): S es N + (N-1) + ... + 1.

suma(1, 1).                       % caso base
suma(N, S) :-                     % caso recursivo
    N > 1,
    N1 is N - 1,
    suma(N1, S1),
    S is N + S1.
