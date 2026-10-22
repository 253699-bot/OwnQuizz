# OwnQuizz

App Android de quiz con preguntas de opción múltiple, hecha con Kotlin, Jetpack Compose y arquitectura MVVM.

## Qué hace
- Carga 10 preguntas base desde un JSON local (`res/raw/preguntas_base.json`).
- Muestra una pregunta a la vez con retroalimentación verde/rojo, aciertos, errores, racha y progreso.
- Permite filtrar por categoría con chips.
- Permite agregar preguntas propias, que se guardan con Jetpack DataStore.
- Muestra una pantalla de resultados con el porcentaje.

## Restricciones
Sin APIs externas, SQLite ni Room.

## Estructura
- `quiz/data`: datasource, model y repository.
- `quiz/di`: QuizModule, que crea el Repository.
- `quiz/presentation`: components, screens y viewmodels.
- `scaffold/screens`: OwnQuizzApp con la barra superior y la navegación.
- `ui/theme`: colores y tema.

## Autora
Aíko Yamileth Del Valle Gutiérrez, matrícula 253699. Universidad Politécnica de Chiapas.