# EJDEN — Journal du projet

## 2026-10-08 — Initialisation du projet KMP

### Architecture
- Nouveau dépôt : https://github.com/Benie-j/EJDEN.git
- Dépôt fonctionnel de référence, à préserver : https://github.com/Benie-j/EJDEN-app.git
- Projet local : ~/EJDEN-KMP
- Branche : main
- Architecture : Kotlin Multiplatform + Compose Multiplatform
- Cible Android : APK
- Cible iOS : prévue, compilation nécessitant un environnement compatible macOS
- Java : 21
- Gradle Wrapper : 9.7.0

### Fichiers ajoutés
- settings.gradle.kts
- build.gradle.kts
- gradle.properties
- .gitignore
- README.md
- gradlew
- gradle/wrapper/gradle-wrapper.jar
- gradle/wrapper/gradle-wrapper.properties
- shared/build.gradle.kts
- shared/src/commonMain/kotlin/com/ejden/shared/App.kt
- androidApp/build.gradle.kts
- androidApp/src/main/AndroidManifest.xml
- androidApp/src/main/java/com/ejden/app/MainActivity.kt
- androidApp/src/main/res/values/strings.xml
- androidApp/src/main/res/values/themes.xml
- .github/workflows/android.yml

### Modifications
- Réparation du script gradlew, initialement vide.
- Configuration du Wrapper pour Gradle 9.7.0.
- Nettoyage de shared/build.gradle.kts pour utiliser les syntaxes recommandées.
- Ajout de la dépendance du module Android vers :shared.
- MainActivity affiche EjdenApp() avec Compose.
- Ajout du workflow GitHub Actions pour compiler l'APK Android et publier l'artefact.

### Sauvegardes locales
- shared/build.gradle.kts.bak
- androidApp/build.gradle.kts.bak

### Vérifications
- ./gradlew --version : RÉUSSI.
- ./gradlew help --no-daemon : RÉUSSI.
- ./gradlew :androidApp:assembleDebug --no-daemon : BLOQUÉ.
- Cause du blocage local : SDK Android introuvable dans Termux.
- Aucune compilation APK confirmée à ce stade.
- Kotlin/Native ne peut pas compiler les cibles iOS depuis linux-arm sous Termux.

### État Git au moment de la rédaction
- Branche : main.
- Dépôt distant origin : https://github.com/Benie-j/EJDEN.git.
- Fichiers du projet non encore ajoutés, commités ou poussés.
- Le dépôt EJDEN-app reste distinct et ne doit pas être modifié par erreur.

## Règle de suivi
À chaque changement, consigner la date, les fichiers ajoutés ou modifiés,
la raison, les vérifications exécutées et leurs résultats réels.
Ne jamais marquer une compilation ou un test comme réussi sans preuve.
