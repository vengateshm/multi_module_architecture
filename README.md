# Multi module architecture
This sample project uses multi module architecture for different layers of clean architecture.

**app** - Main application. The app module includes domain module as a dependency.

**domain** - Implements ui module contract and provide data to ui. This module includes ui module as a dependency.

**ui** - Displays the data in the ui.

**core** - This module contains code that is common across modules and also helper classes.

Every feature will have their own domain and ui modules. Unit tests are written for domain, ui and core modules in their respective module tests folder.
