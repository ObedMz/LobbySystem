# LobbySystem
LobbySystem es un plugin de la api de bungee, creado para manejar el sistema de lobbys de tu servidor.

# Instalación
 - git clone https://github.com/ObedMz/LobbySystem.git
 - cd LobbySystem
 - mvn clean install

Despues de compilar el plugin, agregalo a la carpeta /plugins de tu servidor bungee y listo.
# Requerimientos
 - Maven
 - Java

# Como usarlo
- Para agregar un servidor como lobby puedes agregarlo desde la config.yml del plugin, o viajando al servidor y escribir el comando "/lobbycreate"
- Para eliminar el servidor de la lista de lobbys puedes usar el comando "/lobbyremove" (debes estar en el servidor que deseas eliminar)
  tambien puedes hacerlo borrando el nombre desde la "config.yml" y en el server /lobbyreload" 
- Para ir a la lobby debes ejecutar el commando "/lobby" (Desde config puedes elegir si deseas tener una cuenta regresiva, desactivarla o establecerle un tiempo)
- Para cancelar el teletransporte (La cuenta regresiva debe estar activada) se debe repetir el mismo comando "/lobby"

# Permissions
 - "lobby.create"
 - "lobby.remove"
 - "lobby.reload"
 - "lobby.use"
 
