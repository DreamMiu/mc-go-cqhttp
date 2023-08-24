rootProject.name = "mc-go-cqhttp"

include("plugin")
include("project:core")
include("project:runtime-bukkit")

include("project:module-connect")
findProject(":project:module-connect")?.name = "module-connect"

include("project:module-consoleTool")
findProject(":project:module-consoleTool")?.name = "module-consoleTool"

include("project:module-whitelist")
findProject(":project:module-whitelist")?.name = "module-whitelist"
include("project:module-whitelist")
findProject(":project:module-whitelist")?.name = "module-whitelist"
