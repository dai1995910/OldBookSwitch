package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import model.Book;

public class SearchBookUtils {

	private static final String SEARCH_PATH = "https://api.douban.com/v2/book/isbn/";

	/**
	 * 通过网络查询书籍的信息
	 * 
	 * @return
	 */
	public static Book searchBookOnNet(String isbn) {
		Book book = new Book();
		String jsonStr = getBookJson(isbn);
		if(jsonStr == null || jsonStr.equals("")) {
			return null;
		}
		try {
			Map<String, Object> map = GsonUtils.jsonToMaps(jsonStr);
			List<String> authors = (List<String>) map.get("author");
			Map<String, String> imgs = (Map<String, String>) map.get("images");
			book.setBookName((String) map.get("title"));
			book.setISBN((String) map.get("isbn13"));
			String price = (String) map.get("price");
			String rule = "\\d+\\.\\d+";
			Pattern pattern = Pattern.compile(rule);
			Matcher matcher = pattern.matcher(price);
			if (matcher.find()) {
				price = matcher.group(0);
			}
			book.setPrice(Float.valueOf(price));
			book.setImgUrl(imgs.get("medium"));
			StringBuilder sb = new StringBuilder();
			for (String au : authors) {
				sb.append(au + ";");
			}
			book.setAuthor(sb.toString());
			book.setPublisher((String) map.get("publisher"));
		} catch (DataFormatException e) {
			e.printStackTrace();
			return null;
		}
		return book;
	}

	private static String getBookJson(String isbn) {
		String resultStr = "";
		StringBuilder requestStr = new StringBuilder(SEARCH_PATH);
		if(isbn == null || "".equals(isbn)) {
			return "";
		}
		requestStr.append(isbn);
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL url = new URL(requestStr.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 3.设置参数
			conn.setDoInput(true);// 设置能不能读
			conn.setDoOutput(true);// 设置能不能写
			conn.setReadTimeout(5000);// 连接上了读取超时的时间
			conn.setConnectTimeout(5000);// 设置连接超时时间5s
			conn.setRequestMethod("GET");

			int code = conn.getResponseCode();
			if (code == 200) {
				// 5读取服务器资源的流
				is = conn.getInputStream();
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 准备内存输出流 临时存储的
				String str = null;
				StringBuilder sb = new StringBuilder();
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				resultStr = sb.toString();
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // 进行流的关闭
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
		}
		return resultStr;
	}
}
