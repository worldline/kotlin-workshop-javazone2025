# JavaZone 2025 Kotlin workshop

Weclcome to the Kotlin workshop for JavaZone 2025!

This goal of this workshop is to illustrate how Kotlin is a **write once run everywhere** language.
Indeed, Kotlin can taget Android, iOS, Web, Desktop and Server.

![kmp](assets/kmp.png)

We will see this through different applications types: frontend, backend, AI-powered and data science.
That's why the workshop is divided into 5 parts, where part 1 is a qui ck tour of the Kotlin language and the other
parts are different application types.
Is it recommended to follow part 1 first and you can follow the other ones in any order, but you can stick to the agenda
if you want to experiment first aspects where multiplatform shines the most.

We'll be thus using different frameworks and libraries.
This workshop does not aim to teach all these in detail, but rather to give you a quick overview of what is possible.
So, let's get started!

## Preparation

1. Clone this repository `git clone https://github.com/worldline/kotlin-workshop-javazone2025.git`
1. If you want to use Android Studio, install these plugins
  - Kotlin Multiplatform for macOS users
    and [Compose Multiplatform for Desktop IDE Support](https://plugins.jetbrains.com/plugin/16541-compose-multiplatform-for-desktop-ide-support)
    for Windows and Linux.
  - Kotlin notebooks
1. If you want to use IntelliJ IDEA, install the Android plugin as well
1. Optioanl plugins for both
   IDEs: [Indent rainbow](https://plugins.jetbrains.com/plugin/13308-indent-rainbow), [Rainbow Brackets Lite - Free and OpenSource
   ](https://plugins.jetbrains.com/plugin/20710-rainbow-brackets-lite--free-and-opensource)
1. For macOS users: XCode
1. [gradle](https://gradle.org/install/) (on Windows, you can use [Scoop](https://scoop.sh/)
   or [Chocolatey](https://chocolatey.org/install))

## Part 1 - Tour of the Kotlin language

- Estimated duration: 30 minutes
- Recommended IDE: IntelliJ IDEA (Community or Ultimate)
- It is also possible to follow this part by opening notebooks in GitHub and doing the exercises in the Kotlin
  Playground.

This section is a quick tour of the Kotlin language to get you familiar with its syntax and main features.
It is also a preparation to better tackle the next sections.
When needed, you can go back to this section to check how some language feature works.

1. Open this notebook to try some prominent Kotlin feature [01-01-kotlin-tour.ipynb](./01-01-kotlin-tour.ipynb)
1. Next, let's do some exercises that are listed in the file [01-02-exercises.ipynb](./01-02-exercises.ipynb)

- The solutions are available in [01-02s-solutions.ipynb](./01-02s-solutions.ipynb)

1. Finally, let's quickly see how OOP is done in Kotlin: [01-03-oop.ipynb](./01-03-oop.ipynb)
1. (Optional) For more features, please open this
   file [01-04-more-language-features.ipynb](./01-04-more-language-features.ipynb)

[Solution](https://pl.kotl.in/gafzm3lxw)

## Part 2 - Frontend development

- Estimated duration: 40 minutes
- Recommended IDE: IntelliJ IDEA (Community or Ultimate)

Kotlin supports existing JS and JVM UI frameworks thanks to its multiplatform capabilities.
Meaning that we can code ReactJS or JavaFX applications using Kotlin.
It also has its own UI framework called Compose Multiplatform, which supports desktop (Windows, MacOS, Linux), web and
mobile (Android and iOS), making it a *write once, run anywhere* framework.
Thanks to Kotlin DSL capabilities, UIs can be described in a declarative way, similar to HTML.

In this part, we'll focus on Compose multiplatform, but there is also a bonus section on JavaFX.

1. Compose Multiplatform: [03-01-compose.md](02-01-compose.md)
1. (Bonus) JavaFx with Kotlin: [03-02-javafx.md](02-02-javafx.md)

## Part 3 - AI-powered applications

- Estimated duration: 30 minutes
- Recommended IDE: IntelliJ IDEA (Community or Ultimate)

After the AI boom, many AI libraries have been created.
Kotlin supports the famous JVM langchain4j and has its own AI library called Koog.

We'll focus on Koog in this part since it support the Web and Desktop targets, making it a nice showcase for **write
once run everywhere**.

1. Koog: [03-01-koog.ipynb](./03-01-koog.ipynb)
1. Exercise: Koog + Compose Multiplatform
   application: [03-02-koog-compose-exercise.md](./03-02-koog-compose-exercise.md)
1. (Bonus) Languchain4j:

## Part 4 - backend development

- Estimated duration: 30 minutes
- Recommended IDE: IntelliJ IDEA (Community or Ultimate)

Kotlin supports JVM and JS frameworks.
In addition to that, also has Kotlin exclusive ones (that usually run on the JVM or native).

We'll start with JVM frameworks because they are very popular, then Kotlin native ones.
Finally, we leave JS runtimes as a bonus since it is not as straightforward as the others.

1. JVM frameworks: [04-01-backend-jvm.md](./04-01-backend-jvm.md)
1. Kotlin native frameworks: [04-02-backend-kt.md](./04-02-backend-kt.md)
1. (Bonus) JS frameworks: [04-03-backend-js.md](./04-03-backend-js.md)

## Part 5 - Datascience

- Estimated duration: 30 minutes
- Recommended IDE: IntelliJ IDEA (Community or Ultimate)

With its support of Jupyter notebooks and availability of JVM (such as Spark) and Kotlin native analysis and plotting
libraries, Kotlin is a great language for data science.
