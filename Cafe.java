import java.util.Scanner;
public class Cafe{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String[] menuNames = {"Green Tea","Esspresso","Ameicano","Thai Tea","Latte"};
        double[] menuPrices = {40.00,50.00,50.00,30.00,55.00};
        int[] orderAmount = new int[menuNames.length];
        double sumPrice = 0.0;
        double discount = 0.0;

        while(true){
            System.out.println("========== MENU ==========");
            for(int i = 0; i < menuNames.length;i++){
                System.out.printf("%d. %s ราคา %.2f บาท\n",(i+1),menuNames[i],menuPrices[i]);
            }
            System.out.println("0. Exits");

            System.out.print("ใส่หมายเลขเมนูที่ต้องการ : ");
            int choice = sc.nextInt();

            if(choice == 0){
                System.out.println("ขอบคุณสำหรับการสั่งซื้อครับ");
                break;
            }
            if(choice < 1 || choice > menuNames.length){
                System.out.println("โปรดเลือกหมายเลขเมนูให้ถูกต้อง");
                continue;
            }
            int index = choice -1;
            System.out.println("เลือกเมนู : "+menuNames[index]);

            System.out.print("จำนวนแก้วที่ต้องการ : ");
            int amount = sc.nextInt();

            if(amount > 0){
                orderAmount[index] += amount;
                double totalPrice = menuPrices[index] * amount;
                sumPrice += totalPrice;
            }
            else{
                System.out.println("ใส่จำนวนแก้วให้ถูกต้อง");
            }
        }

        sc.nextLine();

        System.out.print("เป็นสมาชิกหรือไม่(yes or no) : ");
            String member = sc.nextLine();

            if(member.equalsIgnoreCase("yes") || member.equalsIgnoreCase("y")){
                System.out.println("คุณได้รับส่วนลด 10%");
                discount = (sumPrice * 10)/100;
                sumPrice = sumPrice - discount; 
            }
        System.out.println("=============== ใบเสร็จ ===============");
        System.out.println("||  รายการ  ||   จำนวน   ||   ราคา   ||");
        for(int i = 0; i < menuNames.length; i++){
            if(orderAmount[i] > 0){
                System.out.println("   "+menuNames[i] + "     " + orderAmount[i]+ " แก้ว " + "      "+menuPrices[i] + " บาท");
            }
        }

        System.out.println("======================================");

        if(member.equalsIgnoreCase("yes") || member.equalsIgnoreCase("y")){
            System.out.println("เป็นสมาชิกได้รับส่วนลด 10%");
            System.out.println("ลดราคา : "+discount +" บาท");
        }
        else{
            System.out.println("ไม่เป็นสมาชิก");
            System.out.println("ลดราคา : 0 บาท");
        }

        System.out.println("ราคารวมทั้งหมด : "+sumPrice + " บาท");
        System.out.println("======================================");

        sc.close();
    }
}