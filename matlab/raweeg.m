% MATLAB file for plotting RAW EEG data

% x = linspace(-5, 15, 1000);
% y = 10 .^ (x./20) / 6;
% plot(x,y)
clear
close all
x = readtable('../raweeg.txt');

Fs = 256;

titles = x.Properties.VariableNames;

figure;
for i = 1:4
    xc = x{:,i};
    xc = xc - mean(xc);
    subplot(2,2,i);
    %pspectrum(xc, Fs, 'FrequencyResolution', 1)
   
    n = 256;
    spectrogram(xc, hamming(n), n/2, n, Fs)
    caxis([-20; 50])
    colormap hot
    title(['Raw EEG @ ' titles{i}])
end