# LPOO1617_T6G12
Projecto 1 - Diogo Costa (up201507146) e Pedro Oliveira (up201507152)
# Design Patterns
Holder -> Listview scroll performance, recycling views that disappear from the UI;</br>
Composite -> View; </br>
Double Buffer-> Intermediate Object between display and data (performance increase);</br>
Template Method-> Override;</br>
Game Loop-> Control thread speed with sleep (Control of run velocity);</br>
Observer -> listview array changed notifier

# Major difficulties along the way
O desenvolvimento Android tem algumas particularidades, pois o mesmo componente visual pode ser programado quer por JAVA (adicionando-o ao criar a atividade) ou pode ser feito de forma permanente se programado diretamente em xml. Outra particularidade relaciona-se com as permissões que são necessárias para a aplicação que se está a desenvolver. Todos os componentes como web, bluetooth, som ou vibração precisam de permissões individuais que devem ser acordadas pelo utilizador para que a aplicação possa aceder a esses recursos (hardware ou software) do Smartphone.</br>
Ainda outra particularidade do Android é o particionamento da informação em resources, quer sejam objetos de um menu, um layout, os temas, os estilos, as cores, strings fixas. Todas estas e componentes derivadas, com boas práticas, devem ser declaradas em ficheiros xml à parte e guardados em pastas organizadas por tópico já declarados à criação do projeto.</br>
Uma outra questão que teve de ser tida em conta foi o facto do Android não permitir que certos processos corram na Thread de interface com o utilizador por se tratarem de "blocking calls" e poderem tornar a tela irresponsiva, como tal, os pedidos GET e POST, bem como Bluetooth devem ser feitos em threads separadas da "Main Thread" e contatar com ela por meio de Handlers. Classe criada especificamente com este propósito.</br>
No caso do Bluetooth, foi necessário usar BroadcastReceiver para detetar falhas em tempo real.
Da mesma forma, os dados do bluetooth são recebidos através de uma Thread secundária e enviados para Thread de interface (gráfico em tempo real), através de um Handler onde a informação é processada.</br>
Para assegurar a execução de thread em background, mesmo que minimizando a aplicação, recorre-se a Services que se tratam de atividades sem interface responsáveis por manter tarefas entre as tarefas em normal execução no sistema operativo Android.</br>

# Decisões de design

As passwords são guardadas na base de dados em hash, sendo a sua verificação igualmente em hash do lado do servidor. Desta forma, garante-se que a password só está representada em plain text na aplicação.
Um dos objetivos é que o utilizador controla quais os quartos que desejava monitorizar, podendo selecionar e retirar conforme as necessidades.</br>
A requisição de alertas é feita em intervalos de 3 segundos por meio de sucessivos pedidos GET para os quartos pretendidos. Quando novos alertas surgem ou os mesmos alertas persistem há mais de 45 segundos, o utilizador é notificado. Aqui, o utilizador é alertado por som e é criada uma notificação na barra de notificações com informação descritiva dos alertas pendentes, só sendo necessário maximizar a aplicação para resolver os alertas.</br>
Ao tocar nos alertas pendentes na atividade principal, é permitido ao utilizador "resolvê-los", mudando o seu estado na base de dados. Desta forma, por ação do utilizador, é enviado um POST cujo payload tem o id do paciente, id da máquina, número do quarto e id do smartphone. Desta forma, no caso de mais que um utilizador monitorizarem o mesmo quarto, é possível identificar o telemóvel (não necessariamente o utilizador) e guardar a informação do Smartphone que resolveu o alerta. O id do smartphone é uma string de 64 bits específica de cada sistema operativo Android que esteja em execução (Universal).</br>
Quando se pretende a monitorização em tempo real por Bluetooth, a primeira atividade iniciada pede para selecionar o dispositivo a conectar. Esta opção permite uma maior rapidez de processos.</br>
O gráfico e as variáveis fisiológicas (%SpO2, BPM, Stress) são calculadas e ilustradas na aplicação em tempo real (atraso máximo de 5/6 segundos).</br>
O bluetooth é identificado pelo seu endereço MAC, onde é criada uma ligação segura usando UUID.</br>
Capacidade de detecção de falha e notificação do utilizador em tempo útil. Quando minimizada a aplicação durante a ligação bluetooth, esta desconecta temporariamente, voltando a conectar-se ao microcontrolador quando a atividade é novamente aberta. Esta abordagem permite economizar a bateria.</br>

# Distribuição 

Equitativa, 50%, cada um dos elementos encarregou-se de trabalho equivalente.</br>

# Tempo em desenvolvimento

+/- 70 a 90 horas cada elemento </br>

[![BCH compliance](https://bettercodehub.com/edge/badge/s1sm1x/LPOO1617_T6G12?branch=master)](https://bettercodehub.com/)
