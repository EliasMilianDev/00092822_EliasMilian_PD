% reglas.pl
% Ejercicio 4: reglas de razonamiento sobre la isla

:- consult('hechos.pl').

% Puede brindar compania quien ya es aliado o puede llegar a serlo.
e4_companero(Personaje) :-
    ( e4_rol(Personaje, aliado_capturado)
    ; e4_puede_volverse_aliada(Personaje)
    ).

% Una zona esta libre de amenazas si no se registra ningun enemigo en ella.
e4_libre_de_amenazas(Zona) :-
    e4_zona(Zona),
    \+ e4_aparece_en(_, Zona).

% Conviene explorar cuando el peligro es medio y el acceso no exige llave.
e4_exploracion_conveniente(Zona, Momento) :-
    e4_peligro(Zona, Momento, medio),
    \+ e4_requiere_llave(Zona).

% Alguien recibe apoyo para su refugio si un companero sabe construir.
e4_recibe_ayuda_para_refugio(Personaje, Ayudante) :-
    e4_necesita(Personaje, refugio),
    e4_companero(Ayudante),
    e4_habilidad(Ayudante, construir).

% Un material puede recogerse de dia si el peligro de su zona no es alto.
e4_material_recolectable(Material) :-
    e4_material(Material),
    e4_se_encuentra(Material, Zona),
    e4_peligro(Zona, dia, Nivel),
    Nivel \== alto.

% Un lugar con acceso restringido y sin enemigos sirve como escondite.
e4_escondite_protegido(Zona) :-
    e4_libre_de_amenazas(Zona),
    e4_requiere_llave(Zona).
