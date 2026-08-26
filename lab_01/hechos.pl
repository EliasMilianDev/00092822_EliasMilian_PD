% hechos.pl
% Ejercicio 4: base de conocimientos de la isla

% Sobrevivientes
e4_personaje(eric).
e4_personaje(timmy).
e4_personaje(kelvin).
e4_personaje(virginia).

% Informacion conocida de cada personaje
e4_rol(eric, protagonista).
e4_edad(eric, 30).

e4_rol(kelvin, aliado_capturado).
e4_no_habla(kelvin).
e4_habilidad(kelvin, cargar_troncos).
e4_habilidad(kelvin, construir).
e4_requiere_orden(kelvin).

e4_rol(virginia, mutante).
e4_puede_volverse_aliada(virginia).

% Equipo inicial de Eric
e4_tiene(eric, hacha).
e4_tiene(eric, encendedor).

% Lugares principales
e4_zona(superficie).
e4_zona(cuevas).
e4_zona(bunkeres).

% Enemigos y lugares donde aparecen
e4_enemigo(canibales).
e4_enemigo(mutantes).

e4_aparece_en(canibales, superficie).
e4_aparece_en(mutantes, superficie).
e4_aparece_en(mutantes, cuevas).

% Condicion de acceso
e4_requiere_llave(bunkeres).

% Peligro segun la zona y el momento
e4_peligro(superficie, dia, medio).
e4_peligro(superficie, noche, alto).
e4_peligro(cuevas, dia, alto).
e4_peligro(cuevas, noche, alto).

% Necesidades para sobrevivir
e4_necesita(eric, refugio).
e4_necesita(eric, comida).
e4_necesita(eric, agua).

% Recursos disponibles
e4_material(troncos).
e4_material(piedras).

e4_se_encuentra(troncos, superficie).
e4_se_encuentra(piedras, superficie).
