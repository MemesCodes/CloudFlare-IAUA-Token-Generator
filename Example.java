
public class Example {
	public static void main(String[] args) {
		CFTokenGen CF = new CFTokenGen("https://ucp.nordvpn.com/login/","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36");
		System.out.println(CF.GetGoodCookies());
	}
}
