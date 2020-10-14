package de.topobyte.codeforgermany;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.Body;
import de.topobyte.jsoup.components.Div;
import de.topobyte.jsoup.components.Html;
import de.topobyte.jsoup.components.Img;
import de.topobyte.melon.paths.PathUtil;
import de.topobyte.system.utils.SystemPaths;

public class Generate
{

	public static void main(String[] args) throws IOException
	{
		Path path = SystemPaths.CWD.getParent().resolve("hexagon");
		List<Path> files = PathUtil.list(path);

		Pattern pattern = Pattern.compile("(.*)-(.*).svg");

		Html html = HTML.html();
		Body body = html.ac(HTML.body());
		Div container = body.ac(HTML.div("container"));
		Div row = container.ac(HTML.div("row"));

		List<String> filenames = new ArrayList<>();

		for (Path file : files) {
			String filename = file.getFileName().toString();
			filenames.add(filename);
		}

		Collections.sort(filenames);

		for (String filename : filenames) {
			Matcher matcher = pattern.matcher(filename);
			if (matcher.matches()) {
				String city = matcher.group(2);
				String name = city.substring(0, 1).toUpperCase()
						+ city.substring(1);

				Div col = row.ac(HTML.div("col-6 col-md-4 col-lg-3"));
				Div card = col.ac(HTML.div("card mb-4"));
				Img image = card
						.ac(HTML.img(String.format("hexagon/%s", filename)));
				image.addClass("card-img-top");
				Div cardBody = card.ac(HTML.div("card-body"));
				cardBody.ac(HTML.h5(name)).attr("style", "text-align: center");
			}
		}

		Document document = new Document("");

		OutputSettings settings = document.outputSettings();
		settings.charset("UTF-8");
		settings.indentAmount(2);
		settings.prettyPrint(true);
		document.outputSettings(settings);

		document.appendChild(html);

		System.out.println(document);
	}

}
