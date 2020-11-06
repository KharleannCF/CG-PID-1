Consideraciones:

Para la entrega de la tarea consideramos lo siguientes aspectos

Separar los distintos efectos según la clasificación de globales, locales, bordes, geométricos, zoom, data de la imagen y kernel personalizado, en el caso de no haber cargado alguna imagen, algunos efectos utilizaran una imagen de nuestra biblioteca de mánera predeterminada.

En el caso de los filtros globales:
En el caso de los que puedan tener valores variables (como el blanco y negro o brillo por ejemplo) aparece un slider con el cual decidiremos cuanto sumar o apartir de que valor eliminar o no un pixel.

En el caso de los filtros de detección de bordes tenemos distintas opciones podemos calcular los bordes con la primera derivada horizontal de la imagen (la matriz 2x1 [-1,1]), en el caso de Prewitt y Sobel permitimos usar matrices cuadradas de tamaño 3x3, 5x5 y 7x7.

Para el caso de Prewitt en las lineas que no acompañen al pivote (dependiendo de si queremos calcular los bordes en el eje X o Y) la completamos con 1 y -1, en el caso de que se quiera usar la magnitud usamos ambos kernels y calculamos la distancia euclidiana entre los colores resultantes.

Para el caso de Sobel, aumentamos en potencias de 2 los multiplicadores de los kernels a medida que nos acercamos al pivote, igualmene se da la oportunidad de calcular los bordes en el eje X, Y y en la magnitud.

Para el caso de Scharr decidimos dejarlo en una matriz de 3x3 unicamente porque otros casos generar resultado poco intersantes.

Para el caso de Roberts optamos por dejar las matrices cuadradas y con tamaños par 2x2,4x4,6x6, así mismo para estos casos llenamos la matriz de 1 excepto las diagonales principales e inversa para los ejes X o Y respectivamente, asi mismo tambien es posible mostrar los bordes en función de la magnitud.

Para el caso de los Filtros locales, disponemos de 2 sliders para coordinar el tamaño del kernel y el unos radio button para coordinar la vecindad de las de los pixeles para el caso del filtro laplaciano. así mismo para el caso del laplaciano decidimos limitarlo a matrices cuadradas impares de acuerdo al slider de mayor tamaño y se redondea al numero impar más cercano.

En el caso del filtro Gaussiano, usamos el trianguló de pascal para calcular los valores del kernel.

Asi mismo como tratamiento general para todos los kernels que se aplican, en el caso de que se necesite usar un pixel cuyo valor no podemos obtener (pixeles en la posicion 0-1 por ejemplo) usamos el valor del pivote como el valor de estos pixeles, usamos el valor del pivote para de alguna manera evitar aclarar o oscurecer la imagen o evitar usar la imagen de manera "circular".

Para los Zooms, estan los botones para hacer zoomIn y zoomOut y la cantidad de zoom que se quiera hacer se escribe en el campo de texto de enmedio

Para el kernel personalizado, Se escribe el kernel que se quiera aplicar, en el campo de texto de la izquierda la cantidad de filas del kernel, en el campo de texto de abajo la cantidad de columnas del kernel