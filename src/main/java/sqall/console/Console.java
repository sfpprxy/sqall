package sqall.console;

import sqall.executor.SQall;
import sqall.executor.elastic.SQElasticsearch;

import java.util.Scanner;

public class Console {

    private SQall client;

    private void cli() {
        init();
        while (true) {
            try {
                System.out.print("sqall> ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                client.query(input).get().asTable().prettyPrint();
            } catch (Exception e) {
                String code = e.getMessage();
                e.printStackTrace();
                switch (code) {
                    case ErrCode.EMPTY_INPUT:
                        break;
                    case ErrCode.INVALID_INPUT:
                        System.out.println(code);
                        break;
                    default:
//                        e.printStackTrace();
                }
            }
        }
    }

    private void init() {
        client = new SQElasticsearch();
        String ip = "localhost";
        client.connect(ip);
    }

    public static void main(String[] args) {
        Console t = new Console();
        t.cli();
    }
}
