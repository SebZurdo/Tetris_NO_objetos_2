# Presentación

El siguiente archivo "Tetris_no_objetos" presenta un proyecto el cual
buscaba desarrollar un *Tetris* en el lenguaje de programación *Processing*
el programa tiene 970 líneas de código, además de breves descripciones al frente
de ciertas funciones que cumplen un rol esencial dentro de las mecánicas del juego.



Se utilizaron matrices 2x2, 3x3 y 4x4 para plasmar los *tetronimos*, además de un 
arreglo en tres dimensiones para guardar valores de *posición en X*, *posición en Y* y *colores
RGB*, este arreglo fue fundamental durante el desarrollo del proyecto debido a que gracias a este
se lograron implementar las mecánicas de colisiones, borrado de líneas y fin del juego.

# Arreglos

    colores = new int[10][20][3]; //Arreglo 3D para las colisiones
    fllenas = new int[20]; //Arreglo usado para analizar cuando una línea se llena
El uso de arreglos para este proyecto fue esencial, ya que permitieron que se almacenaran valores y así permitir que el programa se desarrollara más rápido

# Puntuación

La puntuación aumenta cada vez que se baja la figura (1 punto) y cada vez que se completa la figura(100 puntos)

