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

    @Autowired
    public ConfiguracaoSeguranca(ProvedorAutenticacaoPersonalizado provedorAutenticacaoPersonalizado) {
        this.provedorAutenticacaoPersonalizado = provedorAutenticacaoPersonalizado;
    }

    private FiltroAutenticacao obterFiltroAutenticacao() {
        return new FiltroAutenticacao();
    }

    private FiltroLogin obterFiltroLogin() throws Exception {
        return new FiltroLogin("/login/", authenticationManager());
    }

    private FiltroDeExceptions obterFiltroDeExceptions() {
        return new FiltroDeExceptions();
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
         * Adiciona um primeiro filtro para autenticação,
         * o filtro de Exceptions realiza a validação e tratamento das Exceptions que ocorrem nos
         * outros filtros, é por meio deste filtro que conseguimos determinar respostas corretas para
         * os clientes que realizam requisições para a API.
         * */
        httpSecurity.addFilterBefore(obterFiltroDeExceptions(), UsernamePasswordAuthenticationFilter.class);

        /*
         * Adiciona um segundo filtro para autenticação,
         * o filtro de login (FiltroLogin) e determina que antes do filtro
         * de login, será utilizado um filtro UsernamePasswordAuthenticationFilter
         * */
        httpSecurity.addFilterBefore(obterFiltroLogin(), UsernamePasswordAuthenticationFilter.class);

        /*
         * Adiciona um terceiro filtro para autenticação,
         * o filtro da autenticação do token (FiltroAutenticacao) e
         * determina que antes do filtro de login, será utilizado um filtro UsernamePasswordAuthenticationFilter
         * */
        httpSecurity.addFilterBefore(obterFiltroAutenticacao(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // Configura o provedor de autenticação do Spring
        auth.authenticationProvider(this.provedorAutenticacaoPersonalizado);
    }
}
