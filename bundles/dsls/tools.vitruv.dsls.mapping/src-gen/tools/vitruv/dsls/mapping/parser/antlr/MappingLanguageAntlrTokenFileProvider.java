/*
 * generated by Xtext 2.12.0-SNAPSHOT
 */
package tools.vitruv.dsls.mapping.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class MappingLanguageAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("tools/vitruv/dsls/mapping/parser/antlr/internal/InternalMappingLanguage.tokens");
	}
}
