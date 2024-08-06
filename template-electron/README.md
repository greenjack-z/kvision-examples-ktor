## Gradle Tasks

### Resource Processing
* generatePotFile - Generates a `src/jsMain/resources/i18n/messages.pot` translation template file.
### Running
* run - Starts a webpack dev server on port 3000.
* runApp - Build complete application and run with Electron.
### Packaging
* jsBrowserDistribution - Bundles the compiled js files into `build/dist/js/productionExecutable`
* zip - Packages a zip archive with all required files into `build/libs/*.zip`
* bundleApp - Bundle Electron application into `build/electron`
