# Multi module architecture
This sample project uses multi module architecture for different layers of clean architecture.

**app** - Main application. The app module includes domain module as a dependency.

**domain** - Implements ui module contract and provide data to ui. This module includes ui module as a dependency.

**ui** - Displays the data in the ui.

Every feature will have their own domain and ui modules.
