% Ejercicio 3: Contar la cantidad de digitos de un numero N (recursividad)
% Uso:  ?- contar_digitos(345, C).
%       C = 3.

% Caso base: un numero de 0 a 9 tiene 1 digito.
contar_digitos(N, 1) :-
    N >= 0,
    N < 10.

% Caso recursivo: quitar el ultimo digito (N // 10) y sumar 1.
contar_digitos(N, C) :-
    N >= 10,
    N1 is N // 10,
    contar_digitos(N1, C1),
    C is C1 + 1.
