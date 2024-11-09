import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class Processo {
    int pid;
    int tp;
    int tempoExecucao;
    int cp;
    String estado;
    int nes;
    int nCpu;
    int quantumRestante;

    public Processo(int pid, int tempoExecucao) {
        this.pid = pid;
        this.tempoExecucao = tempoExecucao;
        this.tp = 0;
        this.cp = 1;
        this.estado = "PRONTO";
        this.nes = 0;
        this.nCpu = 0;
        this.quantumRestante = 1000;
    }

    public void atualizarEstado(String novoEstado) {
        System.out.println("PID " + pid + " " + estado + " >>> " + novoEstado);
        this.estado = novoEstado;
    }

    public void salvarEstado(FileWriter writer) throws IOException {
        writer.write("PID: " + pid + ", TP: " + tp + ", CP: " + cp + ", Estado: " + estado +
                ", NES: " + nes + ", N_CPU: " + nCpu + "\n");
    }

    public boolean executarCiclo(Random random) {
        if (quantumRestante == 0 || tp >= tempoExecucao) {
            return false;
        }

        tp++;
        cp = tp + 1;
        quantumRestante--;

        if (random.nextInt(100) < 1) {
            nes++;
            atualizarEstado("BLOQUEADO");
            return false;
        }

        return tp < tempoExecucao;
    }
}
