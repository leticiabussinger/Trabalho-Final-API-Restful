package br.org.serratec.trabalhoApi.service;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.trabalhoApi.Dtos.UsuarioDto;
import br.org.serratec.trabalhoApi.Dtos.UsuarioInserirDto;
import br.org.serratec.trabalhoApi.exception.EmailException;
import br.org.serratec.trabalhoApi.exception.SenhaException;
import br.org.serratec.trabalhoApi.model.Foto;
import br.org.serratec.trabalhoApi.model.Usuario;
import br.org.serratec.trabalhoApi.repository.FotoRepository;
import br.org.serratec.trabalhoApi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	FotoService fotoService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public List<UsuarioDto> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		List<UsuarioDto> usuariosDto = usuarios.stream().map(u -> {
			return adicionaImagemURI(u);
			}).collect(Collectors.toList());
		
		return usuariosDto;
	}

	public UsuarioDto findById(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isEmpty()) {
			return null;
		}
		
		return adicionaImagemURI(usuarioOpt.get());
	}

	public UsuarioDto inserir(UsuarioInserirDto usuarioInserirDto) throws EmailException, SenhaException {
        Usuario usuario = validateInserir(usuarioInserirDto);
        usuario = usuarioRepository.save(usuario);


        UsuarioDto usuarioDto = new UsuarioDto(usuario);

        return usuarioDto;
    }

	public Boolean deletar(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			usuarioRepository.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	public UsuarioDto atualizar(Long id, UsuarioInserirDto usuarioDto) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			Usuario usuario = validateInserir(usuarioDto);
			usuario.setId(id);
			usuario = usuarioRepository.save(usuario);
			UsuarioDto usuarioDtoAtualizado = new UsuarioDto(usuario);
			return usuarioDtoAtualizado;
		}
		return null;
	}
	
	public Usuario validateInserir(UsuarioInserirDto usuarioInserirDto) {
		if (!usuarioInserirDto.getSenha().equalsIgnoreCase(usuarioInserirDto.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirma Senha devem ser iguais");
		}

		Usuario usuarioEmailExistente = usuarioRepository.findByEmail(usuarioInserirDto.getEmail());
		if (usuarioEmailExistente != null) {
			throw new EmailException("Email já cadastrado.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(usuarioInserirDto.getDataNascimento(), formatter);

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioInserirDto.getNome());
		usuario.setSobrenome(usuarioInserirDto.getSobrenome());
		usuario.setEmail(usuarioInserirDto.getEmail().toLowerCase());
		usuario.setDataNascimento(data);
		usuario.setSenha(bCryptPasswordEncoder.encode(usuarioInserirDto.getSenha()));
		
		return usuario;
	}
	
	public UsuarioDto adicionaImagemURI(Usuario usuario) {
		URI uri = ServletUriComponentsBuilder.
				fromCurrentContextPath()
				.path("/foto/usuario/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		UsuarioDto usuarioDto = new UsuarioDto(usuario);
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuario);
		
		if(fotoOpt.isPresent()) {
			usuarioDto.setUrl(uri.toString());				
		} else {
			usuarioDto.setUrl("Foto não adicionada");
		}
		
		return usuarioDto;	
	}
	
}
