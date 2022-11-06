<h1 align="center">1Coin</h1>

<div align="center">
      (BIG LANDSCAPE IMAGE)
</div>
<div align="center">
  <strong>Simple money manager</strong>
</div>
<div align="center">
  It's superfast, simple and lightweigth.
</div>

<br />

<div align="center">
  <!-- Contributors -->
  <a href="https://github.com/VitalyPeryatin/FinanceTracker/graphs/contributors" alt="Contributors">
        <img src="https://img.shields.io/github/contributors/VitalyPeryatin/FinanceTracker" /></a>
  <!-- Build status -->
  <a href="https://github.com/VitalyPeryatin/FinanceTracker/actions/workflows/android.yml" alt="Build status">
        <img src="https://img.shields.io/github/workflow/status/VitalyPeryatin/FinanceTracker/Android CI" /></a>
  <!-- Stability -->
  <a href="https://nodejs.org/api/documentation.html#documentation_stability_index">
    <img src="https://img.shields.io/badge/stability-experimental-orange.svg?style=flat-square"
      alt="API stability" />
  </a>
  <a href="https://t.me/+FFK1aCS6uJs1NTBi">
        <img src="https://img.shields.io/badge/Telegram-2CA5E0?logo=telegram&logoColor=white"
            alt="chat on Telegram"></a>
</div>

<div align="center">
  <h3>
    <a href="">
      Contributing
    </a>
    <span> | </span>
    <a href="https://t.me/+FFK1aCS6uJs1NTBi">
      Chat
    </a>
  </h3>
</div>

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://github.com/VitalyPeryatin/FinanceTracker/graphs/contributors">
    contributors
  </a>
</div>

## Table of Contents
- [Features](#features)
- [Tech stack](#tech-stack)
- [How to build](#how-to-build)
- [Builds](#builds)

## Features
- __Transactions__
    - Add
    - Edit
    - Delete
- __Accounts__
    - Add
    - Edit
    - Delete
- __Categories__
    - Add
    - Edit
    - Delete
- __Quick info viewing at home tab__

## Tech stack
- [Compose Multiplatform](https://github.com/JetBrains/compose-jb) - UI (Android + Desktop)
- [Odyssey](https://github.com/AlexGladkov/Odyssey) - Navigation
- [KViewModel](https://github.com/adeo-opensource/kviewmodel--mpp) - Shared ViewModel
- [Koin](https://insert-koin.io/) - Dependency injection
- [SqlDelight](https://github.com/cashapp/sqldelight) - SQLite database
- [Napier](https://github.com/AAkira/Napier) - Logger
- [Firebase](https://firebase.google.com/) - Crashlytics
- [LeakCanary](https://square.github.io/leakcanary/) - Android memory leaks checker
- [Chucker](https://github.com/ChuckerTeam/chucker) - Android network logger
- [Detekt](https://github.com/detekt/detekt) - Static code analysis

## How to build
- Android
    - Request <i>'google-services.json'</i> file from <a href="https://t.me/infinity_coder">@infinity_coder</a> in Telegram
    - Add <i>'google-services.json'</i> file in <i>'/android/'</i> folder
    - Sync gradle
    - Run Android configuration project
- Desktop
    - Sync gradle
    - Launch <i>'main()'</i> function in <i>'desktop/src/jvmMain/kotlin/com/finance_tracker/finance_tracker/main.kt'</i>

## Builds
- Windows: -
- MacOS: -
- Linux: -
- Android: -
- iOS: <i>In developing</i>