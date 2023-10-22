package br.org.serratec.trabalhoApi.service;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.serratec.trabalhoApi.exception.FotoException;
import br.org.serratec.trabalhoApi.model.Foto;
import br.org.serratec.trabalhoApi.model.Usuario;
import br.org.serratec.trabalhoApi.repository.FotoRepository;
import br.org.serratec.trabalhoApi.repository.UsuarioRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Foto inserir(Long id, MultipartFile file) throws IOException {

		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuarioOpt.get());
		
		if (fotoOpt.isPresent()) {
			throw new FotoException("Não é possivel inserir a foto, pois o usuario de id " + id + " já possui uma");
		}

		if (file.getSize() > 0) {
			if (usuarioOpt.isPresent()) {
				Foto foto = new Foto();
				foto.setNome(file.getName());
				foto.setTipo(file.getContentType());
				foto.setDados(file.getBytes());
				foto.setUsuario(usuarioOpt.get());

				foto = fotoRepository.save(foto);

				return foto;
			}

		} else {
			throw new FotoException("Não é possivel adicionar uma foto vazia");

		}
		return null;

	}

	@Transactional
	public Foto buscarPorIdUsuario(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isEmpty()) {
			return null;
		}

		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuarioOpt.get());
		if (fotoOpt.isEmpty()) {
			return null;
		}

		return fotoOpt.get();
	}
	
	@Transactional
	public Foto atualizar(Long id, MultipartFile file) throws IOException {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuarioOpt.get());
		
		if (fotoOpt.isPresent()) {
			if (file.getSize() > 0) {
				if (usuarioOpt.isPresent()) {
					Foto foto = new Foto();
					foto.setId(fotoOpt.get().getId());
					foto.setNome(file.getName());
					foto.setTipo(file.getContentType());
					foto.setDados(file.getBytes());
					foto.setUsuario(usuarioOpt.get());

					foto = fotoRepository.save(foto);
					
					return foto;
				}
				
			} else {
				throw new FotoException("Não é possivel adicionar uma foto vazia");
			}
		}
		throw new FotoException("Não é possivel atualizar a foto, pois o usuario de id " + id + " não possui uma");
	}
	
	@Transactional
	public Boolean deletar(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuarioOpt.get());
		if (fotoOpt.isPresent()) {
			fotoRepository.deleteById(fotoOpt.get().getId());
			return true;
		}
		return false;
	}

}
