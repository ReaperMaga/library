package com.github.reapermaga.library.quarkus.exception

import jakarta.validation.groups.Default

interface ValidationGroups {
    interface Post:Default

    interface Put:Default

    interface Delete:Default

    interface Get:Default

    interface Patch:Default
}