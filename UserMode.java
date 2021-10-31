import java.util.Vector;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.ArrayList;

class UserMode extends TempStation
{
	private static Scanner sc;
	private static BufferedReader br;

	static int count;
	static int pay;
	public int sumpay;			// 투입금액을 누적해서 남을 변수
	public int summoney;		// 샐러드 금액을 계산할 변수

	static Password pw;


	static	// 초기화
	{
		// Scanner 인스턴스 생성 
		sc = new Scanner(System.in);

		br = new BufferedReader(new InputStreamReader(System.in));

		pw = new Password();
	}

	// 완성
	// 메뉴 출력 메소드
	public void uMenuPrint()
	{
		if (MStorage.bowl==0)
		{
			System.out.println("용기가 부족해서 판매가 불가능합니다.");
			System.out.println();
			System.out.println("관리자에게 전화주세요...");
			System.out.println("관리자 : 김호진");
			System.out.println("☎ 010-4848-4114");
			System.out.println();
			System.out.println();
			System.out.println("<<전원이 종료됩니다.빠↑밤↓♬>>");
			System.exit(-1);
		}

		System.out.println();
		System.out.println("┌─────────────────────────────────────────┐");
		System.out.println("│                                         │");
		System.out.println("│                                         │");
		System.out.println("│            1 . 패키징 샐러드            │");
		System.out.println("│            2 . 커스텀 샐러드            │");
		System.out.println("│            3 . 랜덤 샐러드              │");
		System.out.println("│            4 . 고객의 소리              │");
		System.out.println("│                                         │");
		System.out.println("│                                         │");
		System.out.println("└─────────────────────────────────────────┘");
		System.out.print(" >> 메뉴 선택(1~4) : ");
	}

	// 완성
	// 메뉴 선택 메소드
	public void uMenuSelect() throws IOException
	{
		do
		{
			Password.sel = sc.nextInt();
		}
		while ( Password.sel!=4646 && (Password.sel<1 || Password.sel>4) );
		// 4646아니면서, 1보다 작고 4보다 크면 입력 다시.
	}


	// 완성
	// 선택된 메뉴 실행에 따른 기능 호출 메소드
	public void uMenuRun() throws IOException
	{
		UPackageSalad ups = new UPackageSalad();

		UCustomSalad ucs = new UCustomSalad();

		URandomSalad urs = new URandomSalad();

		switch (Password.sel)
		{ 
			case Menus.E_ONE : 
				{
					ups.uPackageSalad();
					uDecision();
				} break;
			case Menus.E_TWO   :
				{
					ucs.uCustomMenuSelect();
					uDecision();
				} break;
			case Menus.E_THREE : 
				{
					urs.limitCal();
					urs.cancel();
					urs.randomRun();
					uDecision();
				} break;
			case Menus.E_FOUR : uNote(); break;
			case Menus.E_SECRETCODE : 
				{
					pw.inputPass();

					pw.modePrint();
					pw.modeSelect();
					pw.modeRun();
				}
				//관리자로 돌아가는메소드호출;  break;		
		}
	}

	// 고객의소리 메소드
	public void uNote() throws IOException
	{
		String note;

		System.out.println("────────────────────────────────────");
		System.out.println("                                       ");
		System.out.println("                                       ");
		System.out.println("      ■■■고객의 소리함■■■        ");
		System.out.println("    원하시는 요청 사항을 적어주세요    ");
		System.out.println("                                       ");
		System.out.print("- ");
		note = br.readLine();
		System.out.println("                                       ");
		System.out.println("                                       ");
		System.out.println("────────────────────────────────────");

		ISetup.sbNote.append(note);
		ISetup.sbNote.append("\n");
		System.out.println("♥여러분의 소중한 의견 감사합니다 ^^♥");

		uMenuPrint();
		uMenuSelect();
		uMenuRun();
	}

	// 구매 결정을 물어보는 메소드 -> 연산도 이루어지도록
	public void uDecision() throws IOException
	{
		UCustomSalad ucs = new UCustomSalad();

		TempStation ts = new TempStation();

		// 위에서 담은 재료들..벡터로 받아서 화면에 출력
		for (int i=0; i<temp.size(); i++)
		{
			for (int j=0; j<ISetup.v.size(); j++)
			{
				if (temp.get(i).name.equals(ISetup.v.get(j).name))
				{
					if (ISetup.v.get(j).num < temp.get(i).num)
					{
						System.out.println("[%s] 재고부족");
						System.out.println("재료 선택창으로 되돌아갑니다. 쏘리...");

						ucs.uCustomMenuSelect();
					}
				}
			}
		}

		System.out.println(" =============================================================================");

		System.out.print("  재료      :  ");
		for (int i=0; i<temp.size(); i++)
			System.out.printf("%s ", temp.get(i).name);
		System.out.println();


		int sumkcal=0;	// 칼로리 누적합을 담을 변수
		for (int i=0; i<temp.size(); i++)
			sumkcal += temp.get(i).kcal;
		System.out.printf("  칼로리    :  %dKcal\n", sumkcal);
		

		summoney=0;	// 금액 누적합을 담을 변수
		for (int i=0; i<temp.size(); i++)
			summoney += temp.get(i).money;
		System.out.printf("  금액      :  %d원\n", summoney);


		// System.out.printf("유통기한 : %d일까지...\n", ★);
		Calendar rightNow = Calendar.getInstance();		// 캘린서 클래스 생성

		int y = rightNow.get(Calendar.YEAR);
		int m = rightNow.get(Calendar.MONTH) + 1;
		int d = rightNow.get(Calendar.DATE);
		System.out.printf("  제조일자  :  %d-%d-%d일 \n", y, m, d);	


		int n = (temp.get(0).name == "연어") ?  3 : 5;
		rightNow.add(Calendar.DATE, n);
		//	get(i)에 들어가는 인덱스값에 따라 유통기한을 오늘로부터 추가
		//	연어 true → 3
		//	연어 false → 5


		y = rightNow.get(Calendar.YEAR);
		m = rightNow.get(Calendar.MONTH)+1;
		d = rightNow.get(Calendar.DATE);
		System.out.printf("  유통기한  :  %d-%d-%d일까지...\n", y, m, d);
		System.out.println(" =============================================================================");




		// 선택 (여태까지 담은 샐러드 할껀지 말껀지)
		System.out.print("결제 하시겠습니까? (Y/N) : ");
		Password.con = sc.next().toUpperCase();	

		// yes라면 결제메소드로, no면 단계 1로- 
		if (Password.con.equals("Y"))
		{
			uPayRun(temp);
			uPayment();
		}
			// ★테스트하려고,
		else if(!Password.con.equals("Y"))
		{
			System.out.println("처음으로 돌아갑니다. ");
			
			ts.temp.clear();

			uMenuPrint();
			uMenuSelect();
			uMenuRun();
		}
	 }// end uDecision

	// 결제 확인 메소드
	// uPayment()에서는 temp 쓸일이 없지만, uPayRun에 넘겨줘야 함.
	// 매개변수로 안받고 uDecision에 있던 temp에 접근이 가능..?

	public void uPayment() throws IOException
	{
		TempStation ts = new TempStation();

		System.out.print("<<결제 방식을 선택하세요 (카드/현금)>>");
		Password.con = sc.next();
			if (Password.con.equals("현금"))
			{
				do
				{
					System.out.print("▶ 지불할 금액을 입력하세요 : ");
					pay = sc.nextInt();
					
				}
				while (pay<summoney);
				
				if (pay == summoney)
				{
					System.out.print("♥결제가 완료 되었습니다.♥");
					ISetup.payNum.put(count, pay);

					ts.temp.clear();

					uMenuPrint();
					uMenuSelect();
					uMenuRun();
				}
				else
				{
					// 거스름돈 연산
					// 거스름돈 부족
					Change.changeCal(pay, summoney);
					//			지불한 금액, 메뉴 가격

				}// end if 
			}
			else if (Password.con.equals("카드"))
			{
				System.out.print("♥결제가 완료 되었습니다.♥");

				ts.temp.clear();

				uMenuPrint();
				uMenuSelect();
				uMenuRun();

			}
	}//end uPayment


	public void uPayRun(Vector<Ingredient> temp) // 결제가 완료될 때 만. 데이터를 추합?저장?하는 기능.
	{
		// 1. 판매된 샐러드 (= 결제 횟수) 갯수 카운트
		count ++;
		
		// 2. 용기 수량을 - : // 실시간 연동때문에 1.과 분리.
		MStorage.bowl--;

		// temp를 재료 이름만 뽑아서 누적시킨다.
		ArrayList<String> tempArray= new ArrayList<String>();

		for (int i =0;i<temp.size();i++ )
		{
			tempArray.add(temp.get(i).name);	// 휘발성
			ISetup.payArray.add(temp.get(i).name);	// 누적용
		}
		
		// 실시간으로 재고 차감하는 구문.
		// temp이름과 냉장고에 이름이 같다면 냉장고 수량 차감.
		for (int i =0;i<temp.size();i++ )
		{
			for (int j=0;j<ISetup.v.size();j++)
			{
				if (ISetup.v.get(j).name.equals(temp.get(i).name))
					ISetup.v.get(j).num--;
			}
		}

		temp.clear();

		// 투입받은 금액을 순번 붙여서 Hashtable에 담기


	}//end uPayRun()

}
