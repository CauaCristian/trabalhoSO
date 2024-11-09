import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimuladorSO {
    private static final int QUANTUM = 1000;
    private static final String FILE_PATH = "tabela_processos.txt";
    private List<Processo> processos;
    private Random random;

    public SimuladorSO() {
        this.processos = new ArrayList<>();
        this.random = new Random();
        inicializarProcessos();
    }

    private void inicializarProcessos() {
        processos.add(new Processo(0, 10000));
        processos.add(new Processo(1, 5000));
        processos.add(new Processo(2, 7000));
        processos.add(new Processo(3, 3000));
        processos.add(new Processo(4, 3000));
        processos.add(new Processo(5, 8000));
        processos.add(new Processo(6, 2000));
        processos.add(new Processo(7, 5000));
        processos.add(new Processo(8, 4000));
        processos.add(new Processo(9, 10000));
    }

    public void executarProcessos() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            while (true) {
                boolean processosAtivos = false;

                for (Processo processo : processos) {
                    if (processo.tp >= processo.tempoExecucao) continue;

                    processosAtivos = true;
                    processo.atualizarEstado("EXECUTANDO");
                    processo.nCpu++;

                    for (int i = 0; i < QUANTUM; i++) {
                        if (!processo.executarCiclo(random)) {
                            break;
                        }
                    }

                    if (processo.tp >= processo.tempoExecucao) {
                        System.out.println("Processo " + processo.pid + " finalizado.");
                        processo.salvarEstado(writer);
                    } else if (processo.estado.equals("BLOQUEADO")) {
                        processo.salvarEstado(writer);
                        if (random.nextInt(100) < 30) {
                            processo.atualizarEstado("PRONTO");
                        }
                    } else {
                        processo.atualizarEstado("PRONTO");
                        processo.salvarEstado(writer);
                    }

                    processo.quantumRestante = QUANTUM;
                }

                if (!processosAtivos) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}