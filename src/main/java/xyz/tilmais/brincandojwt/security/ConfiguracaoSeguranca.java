package xyz.tilmais.brincandojwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

    private ProvedorAutenticacaoPersonalizado provedorAutenticacaoPersonalizado;
    private PontoEntradaDasExceptions pontoEntradaDasExceptions;

    @Autowired
    public ConfiguracaoSeguranca(ProvedorAutenticacaoPersonalizado provedorAutenticacaoPersonalizado,
                                 PontoEntradaDasExceptions pontoEntradaDasExceptions) {
        this.provedorAutenticacaoPersonalizado = provedorAutenticacaoPersonalizado;
        this.pontoEntradaDasExceptions = pontoEntradaDasExceptions;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        /*
         * Realiza a configuração de autenticação das requisições no sistema na ordem:
         * - Desabilita Cross-site request forgery
         * - Autoriza o envio de requisições
         * - Permite todos os tipos de requisições para o endpoint: /
         * - Permite todos os tipos de requisições para o endpoint: /login/
         * - Permite todos os tipos de requisições para o endpoint: /usuarios/
         * - Para qualquer outro endpoint do sistema, é necessário autenticação.
         * */
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login/").permitAll()
                .antMatchers("/usuarios/").permitAll()
                .anyRequest()
                .authenticated();

        /*
         * Recupera o manipulador de exception padrão para adicionar um ponto de entrada.
         * O Spring Security para aplicações REST ao invés de redirecionar as requisições
         * de usuários que não estão logados para uma página de login, ele retorna um
         * status HTTP como resposta. O Spring realiza essa ação através dos EntryPoints
         * e por isso, nós alteramos o ponto de entrada (EntryPoint) para um customizado.
         * */
        httpSecurity.exceptionHandling().authenticationEntryPoint(this.pontoEntradaDasExceptions);

        /*
         * Adiciona um primeiro filtro para autenticação,
         * o filtro de login (FiltroLogin) e determina que antes do filtro
         * de login, será utilizado um filtro UsernamePasswordAuthenticationFilter
         * */
        httpSecurity.addFilterBefore(new FiltroLogin("/login/", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        /*
         * Adiciona um segundo filtro para autenticação,
         * o filtro da autenticação do token (FiltroAutenticacao) e
         * determina que antes do filtro de login, será utilizado um filtro UsernamePasswordAuthenticationFilter
         * */
        httpSecurity.addFilterBefore(new FiltroAutenticacao(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // Configura o provedor de autenticação do Spring
        auth.authenticationProvider(this.provedorAutenticacaoPersonalizado);
    }
}
