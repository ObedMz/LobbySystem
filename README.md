<h1> Lobby System </h1>
***
LobbySystem es un plugin de la API de bungee creado para manejar el sistema de lobbys de tu network.

# Short Wiki

## Instalación
Puedes compilarlo desde la linea de comando usando:
```bash
 git clone https://github.com/ObedMz/LobbySystem.git
 cd LobbySystem
 mvn clean install
 ```
 Despues de compilar el plugin, agregalo a la carpeta /plugins de tu servidor bungee
o descargar la ultima version del .jar haciendo click en [descargar](https://github.com/ObedMz/LobbySystem/releases/download/2.0-SNAPSHOT/lobbysystem-2.0-SNAPSHOT.jar)



## Como usarlo
- Para agregar un servidor como lobby puedes agregarlo desde la config.yml del plugin, o viajando al servidor y escribir el comando "/lobbycreate"
- Para eliminar el servidor de la lista de lobbys puedes usar el comando "/lobbyremove (server)"
  tambien puedes hacerlo borrando el nombre desde la "config.yml" y en el server /lobbyreload" 
- Para ir a la lobby debes ejecutar el commando "/lobby" (Desde config puedes elegir si deseas tener una cuenta regresiva, desactivarla o establecerle un tiempo)
- Para cancelar el teletransporte (La cuenta regresiva debe estar activada) se debe repetir el mismo comando "/lobby".

Ver la [wiki](https://github.com/ObedMz/LobbySystem/wiki) para más información.

## Permissions
 - "lobby.create"
 - "lobby.remove"
 - "lobby.reload"
 - "lobby.list"
 - "lobby.transport"
 - "lobby.use" (editable)
 
 ## Comandos
 - /lobbycreate
 - /lobbyremove <server>
 - /lobby (editable)
 - /lobbylist
 - /lobbytransport <target> <objetive>
 
