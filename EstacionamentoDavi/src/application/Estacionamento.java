package application;

import java.util.Scanner;
import entities.Vagas;
import entities.Veiculo;
import entities.Reserva;

public class Estacionamento {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vagas cadastroVagas = new Vagas(sc); 
        Veiculo cadastroVeiculo = new Veiculo(sc, cadastroVagas);
        Reserva reserva = new Reserva(sc, cadastroVagas);

        System.out.println("Seja bem-vindo ao sistema de estacionamento!");

        int opc;
        do {
            System.out.println("Escolha o que deseja fazer:\n" +
                    "1- Cadastrar vaga(s)\n" +
                    "2- Excluir vaga(s)\n" +
                    "3- Listar vaga(s)\n" +
                    "4- Cadastrar veículo\n" +
                    "5- Retirar veículo\n" +
                    "6- Reservar veículo\n" + 
                    "7- Histórico de reservas\n" +
                    "8- Sair\n");
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    cadastroVagas.cadastroVagas();
                    break;
                case 2:
                    cadastroVagas.excluirVagas();
                    break;
                case 3:
                    cadastroVagas.listarVagas();
                    break;
                case 4:
                    cadastroVeiculo.cadastroVeiculo();
                    break;
                case 5:
                    cadastroVeiculo.retirarVeiculo(); 
                    break;
                case 6:
                    reserva.novaReserva();
                    break;
                case 7:
                    reserva.listarReservas(); 
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opc != 8);

        sc.close();
    }
}
