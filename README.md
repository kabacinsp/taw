Aplikacja zaliczeniowa z przedmiotu Technologie i języki internetowe.

Aplikacja oparta jest na pięciu microservicach:

taw-config -> odpowiedzialny jest za konfigurację wszystkich microserviców 
taw-registry -> odpowiedzialny jest za nadawanie nazw każdemu micorservicowi, dzięki czemu nie trzeba kodowac adresów IP
taw-gateway -> serwer proxy, brama do microserviców, w naszym wypadku zezwala na wywołanie microserviców: taw-auth i taw-account
taw-auth -> microservic odpowiedzialny za autoryzację użytkownika. W microservicie mamy usługi logowania, tworzenia konta, i jego edycji.
            dodatkowo microservice zezwala na nadanie tokena przy wywołaniu zewnętrznego prowidera przy użyciu Oauth2, ale
            opcja ta nie jest dostępna w UI
taw-account -> microservice który miał być odpowiedzialny właśnei za zewnętrzną authoryzację, niestety nie udało mi się połączyć
               komponentów UI pomiędzy microservicami
               
Załączony pliki jar w branchu Jar_zaliczenie należy odpalić w odpowiedniej kolejności:
      taw-config -> taw-registry -> taw-gateway -> taw-auth
      
Następnie można sprawdzić czy jary uruchomione zostały poprawnie wchodzą na port 8761, gdzie powinny znajdować się dwa serwisy
taw-auth i taw-gateway

W przypadku stwierdzenia, ze wszystko jest w porządku możemy udać się do microservicu taw-auth poprzez port 8080. Na stronie
startować działa formularz rejestracji użytkownika oraz logowanie. Domyślny login użytkownika to piotr i haslo 1234. 
Microservice pobiera dane z bazy postgresql na porcie 5432 z loginem postgres oraz hasłem postgres.
Po zalogowaniu się do strony (wykorzystany Spring Security) ukazuje się nam strona powitalna. Dla zwykłego użytkownika
nie ma zakodowanych żadnych nowych funkcjonalności, po zalogowaniu się na portal za pomocą loginu admin oraz hasła admin, w prawym
górnym logu po rozwinięciu listy mamy możliwość zarządzania użytkownikami po kliknięciu pola wyboru "Manage users".

