<<<<<<< HEAD
package com.francodavyd.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(@Size(max = 3) List<String> roleListName) {
}
=======
package com.francodavyd.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(@Size(max = 3) List<String> roleListName) {
}
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
